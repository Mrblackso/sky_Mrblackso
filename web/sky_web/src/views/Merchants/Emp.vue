<template>
<!--  <div>-->
<!--    <div>信息</div>-->
<!--    <div>商户 ID：{{ data.MerchantId }}</div>-->
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
    <el-button type="danger" :disabled="!data.selected.length" @click="handleBatchDelete">删除</el-button>
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
    <el-table-column prop="phone" label="手机号" width="150"></el-table-column>
    <el-table-column prop="address" label="地址" width="200"></el-table-column>
    <el-table-column prop="role" label="角色" width="100"></el-table-column>
    <el-table-column label="操作" width="150">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button size="small" type="danger" @click="handleDeleteOne(scope.row.empId)">删除</el-button>
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
</template>


<script setup>
import { reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";

const data = reactive({
  merchantId: 0,
  empList: [],
  searchName: "",
  pageNum: 1,
  pageSize: 10,
  total: 0, // 总条数
  selected: [], // 多选
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
        pageSize: data.pageSize
      }
    });
    if (res.data.code === '200') {
      data.empList = res.data.data.list || [];
      data.total = res.data.data.total || 0;
    } else {
      ElMessage.warning(res.data.message || '查询失败');
    }
  } catch (error) {
    ElMessage.error('网络异常');
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
  ElMessage.info('新增功能待实现');
}

// 编辑
const handleEdit = (row) => {
  ElMessage.info('编辑功能待实现');
}




onMounted(() => {
  searchByName();
});

</script>



<style>

</style>