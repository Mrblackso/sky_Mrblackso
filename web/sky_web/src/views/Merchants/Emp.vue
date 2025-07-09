<template>
  <!--  <div>-->
  <!--    <div>信息</div>-->
  <!--    <div>商户 ID：{{ data.merchantId }}</div>-->
  <!--  </div>-->

  <!-- 搜索区域 -->
  <div style="display: flex; margin-bottom: 20px;">
    <el-input
        v-model="data.searchName"
        placeholder="请输入姓名查找"
        style="width: 200px; padding-right: 10px"
        @keyup.enter="searchByName"
    ></el-input>

    <el-button type="primary" @click="searchByName">查询</el-button>
    <el-button type="success" @click="resetSearch">重置</el-button>
    <el-button type="info" @click="handleAdd">新增</el-button>
    <el-button
        type="danger"
        :disabled="!data.selected.length"
        @click="handleDelete(data.selected)"
    >
      批量删除
    </el-button>
  </div>

  <!-- 员工列表 -->
  <el-table
      :data="data.empList"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="empId" label="员工ID" width="80"></el-table-column>
    <el-table-column prop="empName" label="姓名" width="150"></el-table-column>
    <el-table-column prop="empPhone" label="手机号" width="150"></el-table-column>
    <el-table-column prop="sex" label="性别" width="200"></el-table-column>
    <el-table-column prop="position" label="职位" width="120"></el-table-column>
    <el-table-column label="操作" width="150">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button size="small" type="danger" @click="handleDelete([scope.row.empId])">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <div style="margin-top: 20px;">
    <el-pagination
        layout="sizes, prev, pager, next, jumper, total"
        :total="data.total"
        :page-size="data.pageSize"
        :current-page="data.pageNum"
        :page-sizes="[5, 10, 15, 20]"
        @size-change="handleSizeChange"
        @current-change="searchByName"
    />
  </div>

  <el-dialog v-model="dialogVisible"   width="30%" >
    <template #header>
      <div style="text-align: center; width: 100%; font-size: 16px; font-weight: bold;">
        员工信息表单
      </div>
    </template>
    <template #footer>
      <div style=" width: 100%;">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </div>
    </template>

    <el-form :model="form" label-width="60px"  @submit.prevent="submitForm">
      <el-form-item label="姓名">
        <el-input v-model="form.empName" placeholder="请输入员工姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号" >
        <el-input v-model="form.empPhone" placeholder="请输入员工手机号"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.sex" placeholder="员工性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="form.position" placeholder="请输入员工职位"></el-input>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="deleteVision" width="30%">

  </el-dialog>
</template>


<script setup>
import {reactive, onMounted, ref} from 'vue'
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";
const dialogVisible = ref(false);
// const deleteVision=ref(false);
const formType=ref('add');
const data = reactive({
  merchantId: 0,
  empList: [],
  searchName: "",
  pageNum: 1,
  pageSize: 10,
  total: 0, // 总条数
  selected: [], // 多选
});

const form = reactive({
  empName:"",
  empPhone:0,
  sex:"",
  position:"",
  empId:0,
});

// 健壮获取 merchantId
const merchantInfoStr = localStorage.getItem('MerchantInfo');
if (merchantInfoStr) {
  try {
    const merchantInfo = JSON.parse(merchantInfoStr);
    data.merchantId = merchantInfo.merchantId || 0;
  } catch (e) {
    data.merchantId = 0;
  }
}

// 查询方法，支持分页
const searchByName = async (pageNum) => {
  if (typeof pageNum === 'number') {
    data.pageNum = pageNum;
  }
  try {
    const res = await axios.get('/api/emp/list', {
      params: {
        merchantId: data.merchantId,
        empName: data.searchName,
        pageNum: data.pageNum,
        pageSize: data.pageSize,
        //empId: data.empId
      }
    });
    if (res.data.code === '200') {
      data.empList = res.data.data.list || [];
      data.total = res.data.data.total || 0;
    } else {
      ElMessage.warning(res.data.message || '查询失败');
    }
  } catch (error) {
    ElMessage.error('搜索异常');
    console.error('搜索员工失败:', error);
  }
};

// 重置搜索
const resetSearch = async () => {
  data.searchName = "";
  data.pageNum = 1;
  await searchByName();
}

// 切换每页条数
const handleSizeChange = (newSize) => {
  data.pageSize = newSize;
  data.pageNum = 1;
  searchByName();
}

// 多选
const handleSelectionChange = (val) => {
  data.selected = val.map(item => item.empId);
}

// 新增
const handleAdd = () => {
  formType.value = 'add';
  form.empName="";
  form.empPhone="";
  form.sex="";
  form.position="";
  dialogVisible.value = true;
}
const submitForm = async () => {
  if (!form.empName || !form.empPhone || !form.sex || !form.position) {
    ElMessage.warning("请填写完整信息");
    return;
  }

  try {
    let res;
    const commonParams = {
      merchantId: data.merchantId,
      empName: form.empName,
      empPhone: form.empPhone,
      sex: form.sex,
      position: form.position
    };
    if(formType.value === 'add'){
      res = await axios.post('/api/emp/add', commonParams);
    }else{
      res = await axios.post('/api/emp/edit', { ...commonParams, empId: form.empId });
    }
    if(res.data.code === '200'){
      ElMessage.success(res.data.message || '操作成功');
      dialogVisible.value = false;
      await searchByName();
    }else{
      ElMessage.warning(res.data.message || '操作失败');
    }

  } catch (error) {
    ElMessage.error('后台异常');
  }
};

// 编辑
const handleEdit = (row) => {
  formType.value = 'edit';
  form.empName = row.empName;
  form.empPhone = row.empPhone;
  form.sex = row.sex;
  form.position = row.position;
  form.empId = row.empId;
  dialogVisible.value = true;
}

// 删除方法，支持单个和批量
const handleDelete = async (empIds) => {
  if (!empIds || empIds.length === 0) {
    ElMessage.warning('请选择要删除的员工');
    return;
  }
  try {
    // 二次确认
    await ElMessageBox.confirm(
        `确定要删除${empIds.length > 1 ? '这些' : '该'}员工吗？`,
        '提示',
        { type: 'warning' }
    );
    const res = await axios.delete('/api/emp/delete', {
      data: {
        merchantId: data.merchantId,
        empIds // 数组
      }
    });

    if (res.data.code === '200') {
      ElMessage.success(res.data.message || '删除成功');
      await searchByName();
    } else {
      ElMessage.warning(res.data.message || '删除失败');
    }
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('删除异常');
  }
};

onMounted(() => {
  searchByName();
});

</script>



<style>

</style>