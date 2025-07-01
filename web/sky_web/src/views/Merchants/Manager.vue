<template>

  <div style="flex: 1;height: 60px;min-width: 100vh;background-color: #f5b955;">
    <div  style="align-items: center;display: flex;height: 100%">
        <img src="../../assets/logo.png" alt="" style="width: 40px;height:40px;padding-left: 10px ;padding-right: 6px">
        <span style="color: white;font-size: 25px;padding-left: 10px">商户后台</span>
        <div style="flex:1;"></div>
        <div style="display: flex;align-items: center;height: 100%;width: 140px">
            <img src="../../assets/user.png" alt="" style="width: 40px;height:40px;padding-left: 10px ;padding-right: 10px">
            <span style="color: white;font-size: 20px;">name</span>
        </div>
    </div>
    <!--  以上头部栏-->

    <div style="flex: 1; display: flex;">
      <!-- 左侧菜单栏 -->
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px);">
        <el-menu
            style="width: 200px; height: 100%;"
            :router="true"
            default-active="/merchant/home"
        >
          <el-menu-item index="/merchant/home">
            <el-icon>
              <House/>
            </el-icon>
              系统首页
          </el-menu-item>
          <el-menu-item index="/merchant/menu">
            <el-icon><Memo /></el-icon>
            菜单展示
          </el-menu-item>
          <el-menu-item index="/merchant/emp">
            <el-icon><User /></el-icon>
            员工列表
          </el-menu-item>
          <el-menu-item index="/merchant/order">
            <el-icon><Bell /></el-icon>
            历史订单
          </el-menu-item>
          <el-menu-item index="/merchant/data">
            <el-icon><MessageBox /></el-icon>
            数据统计
          </el-menu-item>
          <el-menu-item>
            <el-icon>
              <SwitchButton/>
            </el-icon>

            退出登录
          </el-menu-item>
        </el-menu>
      </div>

      <!-- 右侧内容显示区 -->
      <div style="flex: 1;padding: 10px ;background-color: #eeeeee">
        <router-view />
      </div>
    </div>


  </div>

<!---->
</template>

<script setup>
import { ElMessage } from 'element-plus'
import {House,Bell,Memo,MessageBox,User,SwitchButton} from '@element-plus/icons-vue'

</script>

<style scoped>
.el-menu .is-active {
  background-color: #CAEDF3FF !important;
}

/*.el-menu .is-active > .el-sub-menu__title {*/
/*  background-color: #c3d3ef !important;*/
/*}*/
</style>