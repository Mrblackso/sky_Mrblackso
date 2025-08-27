<template>
  <!-- 搜索区域 -->
  <div style="display: flex; margin-bottom: 20px;">
    <el-input
        v-model="data.searchName"
        placeholder="请输入菜品名字查找"
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
      :data="data.menuList"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="menuId" label="菜品ID" width="80"></el-table-column>
    <el-table-column label="菜品样图" width="150">
      <template #default="scope">
        <img
            v-if="scope.row.menuPic"
            :src="scope.row.menuPic"
            style="width: 80px; height: 80px; object-fit: cover; border-radius: 4px; cursor: pointer;"
            @click="previewTableImage(scope.row.menuPic)"
        />
        <span v-else>暂无图片</span>
      </template>
    </el-table-column>

    <el-table-column prop="menuName" label="菜品名称" width="150"></el-table-column>
    <el-table-column prop="menuDescription" label="菜品描述" width="150"></el-table-column>
    <el-table-column prop="menuPrice" label="菜品价格" width="150"></el-table-column>

    <el-table-column label="操作" width="150">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button size="small" type="danger" @click="handleDelete([scope.row.menuId])">删除</el-button>
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

  <el-dialog v-model="dialogVisible" width="30%">
    <template #header>
      <div style="text-align: center; width: 100%; font-size: 16px; font-weight: bold;">
        菜品信息表单
      </div>
    </template>

    <el-form :model="form" label-width="100px" @submit.prevent="submitForm">
      <el-form-item label="菜品名称">
        <el-input v-model="form.menuName" placeholder="请输入菜品名称"></el-input>
      </el-form-item>

      <el-form-item label="菜品样图">
        <el-upload
            class="avatar-uploader"
            :show-file-list="false"
            :http-request="customUpload"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="form.menuPic" :src="form.menuPic" class="avatar" alt=""/>
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>

        <!-- 图片操作按钮 -->
<!--        <div v-if="form.menuPic" style="margin-top: 10px;">-->
<!--          <el-button size="small" type="primary" @click="previewImage">预览图片</el-button>-->
<!--          <el-button size="small" type="danger" @click="deleteImage">删除图片</el-button>-->
<!--        </div>-->

        <div style="font-size: 12px; color: #999; margin-top: 12px; margin-left: 5px">
          支持JPG、PNG格式
        </div>
      </el-form-item>


      <el-form-item label="菜品描述">
        <el-input v-model="form.menuDescription" placeholder="请输入菜品描述"></el-input>
      </el-form-item>

      <el-form-item label="菜品价格">
        <el-input v-model="form.menuPrice" placeholder="请输入菜品价格"></el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <div style="width: 100%;">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 图片预览对话框 -->
  <el-dialog v-model="imagePreviewVisible" width="50%">
    <template #header>
      <div style="text-align: center; width: 80%; font-size: 16px; font-weight: bold;">
        图片预览
      </div>
    </template>
    <div style="text-align: center;">
      <img :src="previewImageUrl" style="max-width: 80%; max-height: 80vh;" />
    </div>
  </el-dialog>
</template>

<script setup>
import {reactive, onMounted, ref, computed} from 'vue'
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";
import { Plus } from '@element-plus/icons-vue'

const dialogVisible = ref(false);
const imagePreviewVisible = ref(false);
const previewImageUrl = ref('');
const formType = ref('add');

const data = reactive({
  merchantId: 0,
  menuList: [],
  searchName: "",
  pageNum: 1,
  pageSize: 10,
  total: 0,
  selected: [],
});

const form = reactive({
  merchantId: 0,
  menuName: "",
  menuPic: "",
  menuDescription: "",
  menuPrice: "",
  menuId: 0,
});

// 获取 merchantId
const merchantInfoStr = localStorage.getItem('MerchantInfo');
if (merchantInfoStr) {
  try {
    const merchantInfo = JSON.parse(merchantInfoStr);
    data.merchantId = merchantInfo.merchantId || 0;
  } catch (e) {
    data.merchantId = 0;
  }
}

// 自定义上传函数
const customUpload = async (options) => {
  const { file, onSuccess, onError } = options;
  const formData = new FormData();

  // 添加文件
  formData.append('file', file);

  // 添加 merchantId 参数
  formData.append('merchantId', String(data.merchantId));

  try {
    const response = await axios.post('/api/file/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    // 调用成功回调
    onSuccess(response.data);
  } catch (error) {
    // 调用错误回调
    onError(error);
  }
};

// 查询方法
const searchByName = async (pageNum) => {
  if (typeof pageNum === 'number') {
    data.pageNum = pageNum;
  }
  try {
    const res = await axios.get('/api/menu/list', {
      params: {
        merchantId: data.merchantId,
        menuName: data.searchName,
        pageNum: data.pageNum,
        pageSize: data.pageSize,
      }
    });
    if (res.data.code === '200') {
      data.menuList = res.data.data.list || [];
      data.total = res.data.data.total || 0;
    } else {
      ElMessage.warning(res.data.message || '查询失败');
    }
  } catch (error) {
    ElMessage.error('搜索异常');
    console.error('搜索菜品失败:', error);
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
  data.selected = val.map(item => item.menuId);
}

// 新增
const handleAdd = () => {
  formType.value = 'add';
  // 重置表单
  Object.assign(form, {
    menuName: "",
    menuPic: "",
    menuDescription: "",
    menuPrice: "",
    menuId: 0,
    merchantId: data.merchantId
  });
  dialogVisible.value = true;
}


// 上传成功处理函数
const handleAvatarSuccess = (response, uploadFile) => {
  console.log('Upload response:', response);
  if (response.code === '200') {
    form.menuPic = `/api/file/download?filename=${encodeURIComponent(response.data)}`;
    ElMessage.success('上传成功');
  } else {
    ElMessage.error(response.message || '上传失败');
  }
};
// 提交表单
const submitForm = async () => {
  if (!form.menuName || !form.menuPic || !form.menuDescription || !form.menuPrice) {
    ElMessage.warning("请填写完整信息");
    return;
  }

  // 价格验证
  const price = parseFloat(form.menuPrice);
  if (isNaN(price) || price <= 0) {
    ElMessage.warning("菜品价格必须是大于0的有效数字");
    return;
  }

  try {
    let res;
    const commonParams = {
      merchantId: data.merchantId,
      menuName: form.menuName,
      menuPic: form.menuPic,
      menuDescription: form.menuDescription,
      menuPrice: price
    };

    if(formType.value === 'add'){
      res = await axios.post('/api/menu/add', commonParams);
    } else {
      res = await axios.post('/api/menu/edit', { ...commonParams, menuId: form.menuId });
    }

    if(res.data.code === '200'){
      ElMessage.success(res.data.message || '操作成功');
      dialogVisible.value = false;
      await searchByName();
    } else {
      ElMessage.warning(res.data.message || '操作失败');
    }
  } catch (error) {
    ElMessage.error('后台异常');
    console.error('提交表单错误:', error);
  }
};

// 编辑
const handleEdit = (row) => {
  formType.value = 'edit';
  Object.assign(form, {
    menuName: row.menuName,
    menuPic: row.menuPic,
    menuDescription: row.menuDescription,
    menuPrice: row.menuPrice,
    menuId: row.menuId,
    merchantId: data.merchantId
  });
  dialogVisible.value = true;
}

// 删除方法
const handleDelete = async (menuId) => {
  if (!menuId || menuId.length === 0) {
    ElMessage.warning('请选择要删除的菜品');
    return;
  }
  try {
    await ElMessageBox.confirm(
        `确定要删除${menuId.length > 1 ? '这些' : '该'}菜品吗？`,
        '提示',
        { type: 'warning' }
    );
    const res = await axios.delete('/api/menu/delete', {
      data: {
        merchantId: data.merchantId,
        menuIds: menuId
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

// // 图片预览方法
// const previewImage = () => {
//   if (form.menuPic) {
//     previewImageUrl.value = form.menuPic;
//     imagePreviewVisible.value = true;
//   }
// }
// // 删除图片功能
// const deleteImage = () => {
//   form.menuPic = '';
//   ElMessage.success('图片已删除');
// };
// 表格中图片预览方法
const previewTableImage = (url) => {
  if (url) {
    previewImageUrl.value = url;
    imagePreviewVisible.value = true;
  }
}

onMounted(() => {
  searchByName();
});
</script>

<style>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
  object-fit: cover;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  line-height: 178px;
}
</style>