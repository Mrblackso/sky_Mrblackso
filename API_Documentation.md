# 菜品图片上传功能 API 文档

## 基础信息
- 基础URL: `http://localhost:8888`
- 请求格式: `multipart/form-data` (文件上传)
- 响应格式: `application/json`

## API 接口

### 1. 获取所有菜品
**GET** `/menu/list`

**响应示例:**
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "menuId": 1,
      "menuName": "宫保鸡丁",
      "menuPic": "/images/kungpao-chicken.jpg",
      "menuDescription": "经典川菜，鸡肉鲜嫩，花生香脆，口感丰富",
      "menuPrice": 28.00,
      "merchantId": 1
    }
  ]
}
```

### 2. 根据商家ID获取菜品
**GET** `/menu/merchant/{merchantId}`

**参数:**
- `merchantId`: 商家ID (路径参数)

### 3. 根据ID获取菜品详情
**GET** `/menu/{menuId}`

**参数:**
- `menuId`: 菜品ID (路径参数)

### 4. 添加菜品（包含图片上传）
**POST** `/menu/add`

**参数:**
- `menuName`: 菜品名称 (必填)
- `menuDescription`: 菜品描述 (必填)
- `menuPrice`: 菜品价格 (必填)
- `merchantId`: 商家ID (必填)
- `imageFile`: 菜品图片文件 (可选)

**请求示例:**
```bash
curl -X POST http://localhost:8888/menu/add \
  -F "menuName=宫保鸡丁" \
  -F "menuDescription=经典川菜，鸡肉鲜嫩" \
  -F "menuPrice=28.00" \
  -F "merchantId=1" \
  -F "imageFile=@/path/to/image.jpg"
```

**响应示例:**
```json
{
  "code": "200",
  "message": "操作成功",
  "data": "菜品添加成功"
}
```

### 5. 更新菜品信息
**POST** `/menu/update`

**参数:**
- `menuId`: 菜品ID (必填)
- `menuName`: 菜品名称 (必填)
- `menuDescription`: 菜品描述 (必填)
- `menuPrice`: 菜品价格 (必填)
- `imageFile`: 菜品图片文件 (可选)

### 6. 删除菜品
**DELETE** `/menu/{menuId}`

**参数:**
- `menuId`: 菜品ID (路径参数)

### 7. 搜索菜品
**GET** `/menu/search?menuName={menuName}`

**参数:**
- `menuName`: 菜品名称关键词 (查询参数)

### 8. 仅上传图片
**POST** `/menu/upload-image`

**参数:**
- `imageFile`: 图片文件 (必填)

**响应示例:**
```json
{
  "code": "200",
  "message": "操作成功",
  "data": "/images/abc123.jpg"
}
```

## 图片访问

### 原图访问
- URL格式: `http://localhost:8888/images/{filename}`
- 示例: `http://localhost:8888/images/abc123.jpg`

### 缩略图访问
- URL格式: `http://localhost:8888/thumbnails/{filename}`
- 示例: `http://localhost:8888/thumbnails/thumb_abc123.jpg`

## 错误处理

### 常见错误响应
```json
{
  "code": "500",
  "message": "图片上传失败：不支持的文件类型，只支持JPG、PNG、GIF、BMP格式",
  "data": null
}
```

### 错误码说明
- `200`: 操作成功
- `500`: 系统错误

## 文件上传限制

- **文件大小**: 最大 5MB
- **支持格式**: JPG、PNG、GIF、BMP
- **存储路径**: `uploads/images/`
- **缩略图**: 自动生成 200x200 像素的缩略图

## 前端调用示例

### JavaScript (使用 FormData)
```javascript
// 添加菜品
const formData = new FormData();
formData.append('menuName', '宫保鸡丁');
formData.append('menuDescription', '经典川菜');
formData.append('menuPrice', '28.00');
formData.append('merchantId', '1');
formData.append('imageFile', fileInput.files[0]);

fetch('/menu/add', {
  method: 'POST',
  body: formData
})
.then(response => response.json())
.then(data => {
  console.log('添加成功:', data);
});
```

### Vue.js 示例
```javascript
// 在 Vue 组件中
methods: {
  async addMenu() {
    const formData = new FormData();
    formData.append('menuName', this.menuName);
    formData.append('menuDescription', this.menuDescription);
    formData.append('menuPrice', this.menuPrice);
    formData.append('merchantId', this.merchantId);
    
    if (this.imageFile) {
      formData.append('imageFile', this.imageFile);
    }
    
    try {
      const response = await fetch('/menu/add', {
        method: 'POST',
        body: formData
      });
      const result = await response.json();
      
      if (result.code === '200') {
        this.$message.success('菜品添加成功');
      } else {
        this.$message.error(result.message);
      }
    } catch (error) {
      console.error('添加失败:', error);
    }
  }
}
``` 