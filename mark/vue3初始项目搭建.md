## Vue

### 创建vue3 项目

```java
npm create vue@latest
```

### 选择 Router 路由管理 ✔

### vue3 初始化

```vue
//项目初始化完成，可执行以下命令：

   cd sky_web
   npm install
   npm run dev

```



### main.js

```vue
//引入createApp 用于 创建 应用
import { createApp } from 'vue'
//引入 app 根组件
import App from './App.vue'
//引入路由组件
import router from './router'

const app = createApp(App)
//创建app 前端应用 放到'app'里
app.use(router)
//启用 router 
app.mount('#app')
//mount 挂载


```

### App.vue  "根"

```vue
<template>
  <router-view/>
</template>
```

<router-view/>它会根据 index.js 中定义的路由规则加载对应的页面组件

### index.js

```vue
import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  //路由模式
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {path:'/' , redirect: '/login'},
    {
      path: '/manager', component: () => import('../views/Manager.vue'),
      children: [

      ]
    },
    {path: '/login', name: 'login', meta: {title: '登录'}, component: () => import('../views/Login.vue')},
    {path: '/register', name: 'register', meta: {title: '注册'}, component: () => import('../views/Register.vue')},
    {path: '/404', name: 'NotFound', meta: {title: 'error'}, component: () => import('../views/404.vue')},
    {path: '/:pathMatch(.*)', redirect: '/404'},


  ],
})

export default router


```

### 安装 element-plus 组件

```vue
npm install element-plus
```

