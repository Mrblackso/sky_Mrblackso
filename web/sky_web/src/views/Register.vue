<template>
  <div class="contain">
    <div class="login_container">
      <!-- 旁白区域 -->
      <div class="sidebar">
        <h2 style="color: #e7a52c">欢迎登录|注册</h2>
      </div>

      <!-- 分隔线 -->
      <div class="divider"></div>

      <!-- 登录表单区域 -->
      <div class="login_box">
        <div class="form">
          <el-form :model="form" :rules="rules" ref="registerFormRef">
            <el-form-item prop="phone">
              <el-input v-model="form.phone" placeholder="请输入账号(手机号)" :prefix-icon="User" style="height: 50px; font-size: 16px;"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="form.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" style="height: 50px; font-size: 16px;"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword">
              <el-input v-model="form.confirmPassword" type="password" placeholder="再次确认密码" :prefix-icon="Lock" style="height: 50px; font-size: 16px;"></el-input>
            </el-form-item>
            <el-form-item prop="role" style="height: 50px; font-size: 16px;">
              <el-select v-model="form.role" placeholder="请选择角色">
                <el-option label="商户" value="Merchant"></el-option>
                <el-option label="用户" value="User"></el-option>
              </el-select>
            </el-form-item>
          </el-form>

          <div class="button-group">
            <el-button @click="goToLogin"  round>返回登录</el-button>
            <el-button type="primary" round @click="onRegister">注册</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {House,Bell,Memo,MessageBox,User,SwitchButton,Lock} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {useRouter} from 'vue-router'
import {ref} from 'vue'
import axios from 'axios'

const router = useRouter()


const form = ref({
  phone: '',
  password: '',
  confirmPassword: '',
  role: ''
})

const rules = {

  phone: [ { required: true, message: '请输入账号', trigger: 'blur' } ],
  password: [ { required: true, message: '请输入密码', trigger: 'blur' } ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
        if (value !== form.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      }, trigger: 'blur' }
  ],
  role: [ { required: true, message: '请选择角色', trigger: 'change' } ]
}
const registerFormRef = ref()

const goToLogin = () => {
  router.push('/login')
}

const onRegister = () => {
  registerFormRef.value.validate(async (valid) => {
    if (!valid) return

    try {
      const response = await axios.post('/api/register', {
        Phone: form.value.phone,
        Password: form.value.password,
        Role: form.value.role
      })

      if (response.data.code === "200") {
        ElMessage.success('注册成功，请登录')
        await router.push('/login')
      } else {
        ElMessage.error(response.data.message || '注册失败')
      }
    } catch (error) {
      ElMessage.error('异常状态')
      console.error(error)
    }
  })
}
</script>

<style scoped>
.contain {
  display: flex;
  justify-content: center;
  min-height: 100vh;
  /*align-items: center;*/
  background: url("../assets/bg.jpg");
  background-size: cover;

}

.login_container {
  display: flex;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 1000px; /* 整体宽度 */
  height: 60vh;
  max-width: 90%; /* 响应式适应 */
  border: 1px solid rgba(64, 158, 255, 0.3); /* 半透明蓝色边框 */
  position: relative;
  margin-top: 20vh;
  margin-bottom: 20vh;
}

.sidebar {
  padding: 40px;
  width: 300px;
  background: rgba(64, 158, 255, 0.1); /* 浅蓝色背景 */
  align-items: center;
  justify-content: center;
  display: flex;
}

.divider {
  width: 1px;
  background: linear-gradient(to bottom, transparent, #cbc7c7, transparent);
}

.login_box {
  flex: 1;
  padding: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(64, 158, 255, 0.1);
}
.form {
  width: 80%;

}

</style>