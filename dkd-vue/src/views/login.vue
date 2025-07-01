<template>
  <div class="login">
    <div class="login-container">
      <div class="login-left animated-bg">
        <div class="slogan">
          <h2>智慧实验管理</h2>
          <p>让实验资源管理更高效</p>
          <div class="decoration-circles">
            <div class="circle circle-1"></div>
            <div class="circle circle-2"></div>
            <div class="circle circle-3"></div>
          </div>
        </div>
      </div>
      <div class="login-right">
        <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
          <div class="form-header">
            <h3 class="title">大学智慧实验管理系统</h3>
            <p class="subtitle">欢迎回来，请登录您的账号</p>
          </div>
          <el-form-item prop="username" class="custom-form-item">
            <el-input
                v-model="loginForm.username"
                type="text"
                size="large"
                auto-complete="off"
                placeholder="账号"
                class="custom-input"
            >
              <template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password" class="custom-form-item">
            <el-input
                v-model="loginForm.password"
                type="password"
                size="large"
                auto-complete="off"
                placeholder="密码"
                @keyup.enter="handleLogin"
                class="custom-input"
            >
              <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
            </el-input>
          </el-form-item>
          <el-form-item prop="code" v-if="captchaEnabled" class="custom-form-item">
            <el-input
                v-model="loginForm.code"
                size="large"
                auto-complete="off"
                placeholder="验证码"
                style="width: 63%"
                @keyup.enter="handleLogin"
                class="custom-input"
            >
              <template #prefix><svg-icon icon-class="validCode" class="el-input__icon input-icon" /></template>
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>
          <div class="remember-me">
            <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
            <a href="javascript:void(0)" class="forgot-password">忘记密码?</a>
          </div>
          <el-form-item style="width:100%;">
            <el-button
                :loading="loading"
                size="large"
                type="primary"
                class="login-button"
                @click.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div class="register-link" v-if="register">
              <span>还没有账号?</span>
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>大学智慧实验管理系统 © {{ new Date().getFullYear() }}</span>
    </div>
  </div>
</template>

<script setup>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const route = useRoute();
const router = useRouter();
const { proxy } = getCurrentInstance();

const loginForm = ref({
  username: "admin",
  password: "admin123",
  rememberMe: false,
  code: "",
  uuid: ""
});

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }]
};

const codeUrl = ref("");
const loading = ref(false);
// 验证码开关
const captchaEnabled = ref(true);
// 注册开关
const register = ref(false);
const redirect = ref(undefined);

watch(route, (newRoute) => {
  redirect.value = newRoute.query && newRoute.query.redirect;
}, { immediate: true });

function handleLogin() {
  proxy.$refs.loginRef.validate(valid => {
    if (valid) {
      loading.value = true;
      // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
      if (loginForm.value.rememberMe) {
        Cookies.set("username", loginForm.value.username, { expires: 30 });
        Cookies.set("password", encrypt(loginForm.value.password), { expires: 30 });
        Cookies.set("rememberMe", loginForm.value.rememberMe, { expires: 30 });
      } else {
        // 否则移除
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove("rememberMe");
      }
      // 调用action的登录方法
      userStore.login(loginForm.value).then(() => {
        const query = route.query;
        const otherQueryParams = Object.keys(query).reduce((acc, cur) => {
          if (cur !== "redirect") {
            acc[cur] = query[cur];
          }
          return acc;
        }, {});
        router.push({ path: redirect.value || "/", query: otherQueryParams });
      }).catch(() => {
        loading.value = false;
        // 重新获取验证码
        if (captchaEnabled.value) {
          getCode();
        }
      });
    }
  });
}

function getCode() {
  getCodeImg().then(res => {
    captchaEnabled.value = res.captchaEnabled === undefined ? true : res.captchaEnabled;
    if (captchaEnabled.value) {
      codeUrl.value = "data:image/gif;base64," + res.img;
      loginForm.value.uuid = res.uuid;
    }
  });
}

function getCookie() {
  const username = Cookies.get("username");
  const password = Cookies.get("password");
  const rememberMe = Cookies.get("rememberMe");
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password: password === undefined ? loginForm.value.password : decrypt(password),
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
  };
}

getCode();
getCookie();
</script>

<style lang='scss' scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  position: relative;
  overflow: hidden;
}

.login-container {
  display: flex;
  width: 900px;
  height: 600px;
  border-radius: 15px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  background-color: #fff;
  position: relative;
  z-index: 1;
}

.login-left {
  width: 40%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  position: relative;
  overflow: hidden;
}

.animated-bg::before {
  content: '';
  position: absolute;
  width: 150%;
  height: 150%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 10%, transparent 10%) 0 0,
  radial-gradient(circle, rgba(255,255,255,0.1) 10%, transparent 10%) 25px 25px;
  background-size: 50px 50px;
  animation: moveBackground 10s linear infinite;
  top: -25%;
  left: -25%;
}

@keyframes moveBackground {
  0% {
    transform: translateY(0) rotate(0deg);
  }
  100% {
    transform: translateY(-50px) rotate(5deg);
  }
}

.slogan {
  color: white;
  text-align: center;
  position: relative;
  z-index: 1;
}

.slogan h2 {
  font-size: 36px;
  margin-bottom: 10px;
  font-weight: 700;
}

.slogan p {
  font-size: 18px;
  opacity: 0.8;
}

.decoration-circles {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
}

.circle-1 {
  width: 100px;
  height: 100px;
  top: 10%;
  left: 10%;
  animation: float 6s ease-in-out infinite;
}

.circle-2 {
  width: 150px;
  height: 150px;
  bottom: 10%;
  right: 10%;
  animation: float 8s ease-in-out infinite;
}

.circle-3 {
  width: 70px;
  height: 70px;
  top: 50%;
  left: 50%;
  animation: float 5s ease-in-out infinite;
}

@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
  100% {
    transform: translateY(0px);
  }
}

.login-right {
  width: 60%;
  padding: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-form {
  width: 100%;
  max-width: 400px;
}

.form-header {
  margin-bottom: 30px;
}

.title {
  font-size: 28px;
  color: #333;
  margin-bottom: 10px;
  text-align: center;
  font-weight: 600;
}

.subtitle {
  font-size: 14px;
  color: #888;
  text-align: center;
  margin-bottom: 20px;
}

.custom-form-item {
  margin-bottom: 25px;
}

.custom-input {
  height: 50px;

  :deep(input) {
    height: 50px;
    padding-left: 45px;
    border-radius: 50px;
    background-color: #f7f9fc;
    border: 1px solid #e1e5ef;
    transition: all 0.3s;

    &:focus {
      background-color: #fff;
      box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
      border-color: #667eea;
    }
  }

  :deep(.el-input__wrapper) {
    box-shadow: none !important;
    border-radius: 50px;
  }
}

.input-icon {
  height: 20px;
  width: 20px;
  margin-left: 15px;
  color: #888;
}

.remember-me {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
}

.forgot-password {
  color: #667eea;
  font-size: 14px;
  text-decoration: none;
  transition: color 0.3s;

  &:hover {
    color: #764ba2;
    text-decoration: underline;
  }
}

.login-button {
  height: 50px;
  width: 100%;
  border-radius: 50px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 7px 14px rgba(50, 50, 93, 0.1), 0 3px 6px rgba(0, 0, 0, 0.08);
  }

  &:active {
    transform: translateY(1px);
  }
}

.register-link {
  margin-top: 20px;
  text-align: center;
  color: #888;

  .link-type {
    color: #667eea;
    margin-left: 5px;
    text-decoration: none;
    font-weight: 600;

    &:hover {
      text-decoration: underline;
      color: #764ba2;
    }
  }
}

.login-code {
  width: 33%;
  height: 50px;
  float: right;
  display: flex;
  align-items: center;
  justify-content: center;

  img {
    height: 40px;
    cursor: pointer;
    border-radius: 5px;
    transition: transform 0.3s;

    &:hover {
      transform: scale(1.05);
    }
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #555;
  font-size: 13px;
  letter-spacing: 1px;
  z-index: 0;
}

@media (max-width: 992px) {
  .login-container {
    width: 95%;
    height: auto;
    flex-direction: column;
  }

  .login-left {
    width: 100%;
    height: 200px;
  }

  .login-right {
    width: 100%;
    padding: 30px 20px;
  }
}
</style>
