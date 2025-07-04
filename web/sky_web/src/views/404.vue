<template>
  <div class="notfound-container">
    <h1>404</h1>
    <p>页面未找到</p>
    <el-button type="primary" @click="goHome">返回首页</el-button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
const goHome = () => {
  router.push('/login')
}
</script>

<style scoped>
.notfound-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 80vh;
}
h1 {
  font-size: 5rem;
  color: #e7a52c;
  margin-bottom: 1rem;
}
p {
  font-size: 1.5rem;
  color: #888;
  margin-bottom: 2rem;
}
</style>