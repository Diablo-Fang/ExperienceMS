<template>
  <div class="home-container">
    <!-- 背景装饰 -->
    <div class="decorative-circle circle-1"></div>
    <div class="decorative-circle circle-2"></div>
    <div class="decorative-circle circle-3"></div>

    <!-- 导航栏 -->
    <el-header class="nav-header">
      <div class="nav-content">
        <div class="logo-container">
          <el-icon class="logo-icon"><School /></el-icon>
          <h1 class="logo">智慧实验管理系统</h1>
        </div>
        <div class="nav-items">
          <el-button class="nav-link" type="text" @click=" router.push('/experiment1');">
            <el-icon><Calendar /></el-icon>
            实验数据管理
          </el-button>
          <el-button class="nav-link" type="text" @click=" router.push('/student');">
            <el-icon><Document /></el-icon>
            学生管理
          </el-button>
          <el-button class="nav-link" type="text" @click=" router.push('/teacher');">
            <el-icon><Collection /></el-icon>
            教师管理
          </el-button>

        </div>
      </div>
    </el-header>

    <div class="main-content">
      <!-- 轮播图模块 -->
      <div class="carousel-section">
        <el-carousel height="400px" indicator-position="outside" :interval="5000" arrow="always" class="main-carousel">
          <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
            <div class="carousel-content" :style="{ backgroundImage: `url(${item.image})` }">
              <div class="carousel-overlay"></div>
              <div class="carousel-text">
                <h2 class="carousel-title">{{ item.title }}</h2>
                <p class="carousel-desc">{{ item.description }}</p>
                <el-button type="primary" size="large" class="carousel-btn" v-if="item.buttonText">
                  {{ item.buttonText }}
                </el-button>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>

      <!-- 数据看板模块 -->
      <div class="dashboard-section">
        <h3 class="section-title">实验资源实时监控</h3>
        <div class="section-subtitle">全面掌握实验资源使用情况，优化实验资源配置</div>
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="6" v-for="(stat, index) in stats" :key="index">
            <el-card class="stat-card" shadow="hover">
              <el-icon :size="36" class="stat-icon" :color="colors[index % colors.length]">
                <component :is="stat.icon" />
              </el-icon>
              <el-statistic
                  :title="stat.title"
                  :value="stat.value"
                  :precision="stat.precision || 0"
                  class="stat-value"
                  :value-style="{ color: colors[index % colors.length], fontSize: '28px' }"
              >
                <template #suffix>{{ stat.unitText }}</template>
              </el-statistic>
              <div class="stat-desc">{{ stat.description }}</div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 核心功能模块 -->
      <div class="core-features">
        <h3 class="section-title">系统核心功能</h3>
        <div class="section-subtitle">一站式解决实验管理难题，提供全方位解决方案</div>
        <el-row :gutter="30">
          <el-col v-for="(feature, index) in features" :key="index" :xs="24" :sm="12" :md="8">
            <el-card class="feature-card" shadow="hover">
              <el-icon :size="48" class="feature-icon" :color="colors[index % colors.length]">
                <component :is="feature.icon" />
              </el-icon>
              <h4 class="feature-title">{{ feature.title }}</h4>
              <p class="feature-desc">{{ feature.desc }}</p>
              <el-button text class="feature-btn">了解更多</el-button>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 流程可视化 -->
      <div class="process-section">
        <h3 class="section-title">实验预约工作流程</h3>
        <div class="section-subtitle">四步完成高效实验预约，省时省力更高效</div>
        <el-card class="process-card">
          <el-steps :active="3" align-center finish-status="success">
            <el-step title="预约申请" description="选择实验时间段，提交预约请求">
              <template #icon>
                <el-icon><Document /></el-icon>
              </template>
            </el-step>
            <el-step title="资源检查" description="系统自动检测实验室状态与可用性">
              <template #icon>
                <el-icon><Setting /></el-icon>
              </template>
            </el-step>
            <el-step title="确认反馈" description="接收预约结果通知，查看实验详情">
              <template #icon>
                <el-icon><EditPen /></el-icon>
              </template>
            </el-step>
            <el-step title="进行实验" description="前往实验室进行实验">
              <template #icon>
                <el-icon><MagicStick /></el-icon>
              </template>
            </el-step>
          </el-steps>
        </el-card>
      </div>

    </div>

    <!-- 底部信息 -->
    <el-footer class="main-footer">
      <div class="footer-content">
        <div class="footer-section about-section">
          <h3 class="footer-title">关于我们</h3>
          <p class="footer-text">智慧实验管理系统致力于为高校提供高清HMI便捷操控、多仪器自动化控制与数据实时采集处理。</p>
        </div>
        <div class="footer-section contact-section">
          <h3 class="footer-title">技术支持</h3>
          <p class="footer-text"><el-icon><Phone /></el-icon> 服务热线：400-800-1024</p>
          <p class="footer-text"><el-icon><Message /></el-icon> 电子邮箱：15755251183@163.com</p>
        </div>
        <div class="footer-section social-section">
          <h3 class="footer-title">关注我们</h3>
          <div class="social-icons">
            <el-button circle class="social-icon"><el-icon><Wechat /></el-icon></el-button>
            <el-button circle class="social-icon"><el-icon><Link /></el-icon></el-button>
            <el-button circle class="social-icon"><el-icon><Coordinate /></el-icon></el-button>
          </div>
        </div>
      </div>
      <div class="copyright">
        © 2025 智慧实验管理系统 版权所有
      </div>
    </el-footer>
  </div>
</template>

<script setup>
import {
  Calendar,
  Setting,
  School,
  MagicStick,
  Link,
  Coordinate,
  Document,
  DataAnalysis,
  Collection,
  VideoPlay,
  EditPen,
  Phone,
  Message
} from '@element-plus/icons-vue'

import { useRouter } from 'vue-router';

const router = useRouter();
const colors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#909399']


// 轮播图数据
const carouselItems = ref([
  {
    title: "实验预约系统",
    description: "高效精准的实验室预约管理",
    image: "https://picsum.photos/1600/500?random=1",
    buttonText: "立即体验"
  },
  {
    title: "零冲突预约机制",
    description: "自动检测并避免资源冲突，确保预约流程顺畅高效",
    image: "https://picsum.photos/1600/500?random=2",
    buttonText: "了解详情"
  },
  {
    title: "可视化资源管理",
    description: "直观展示实验资源使用状态，支持拖拽式操作界面",
    image: "https://picsum.photos/1600/500?random=3",
    buttonText: "查看演示"
  },
  {
    title: "智能决策支持",
    description: "通过数据分析与可视化呈现，辅助管理者进行科学决策",
    image: "https://picsum.photos/1600/500?random=4",
    buttonText: "查看详情"
  }
]);

// 核心功能数据
const features = ref([
  {
    title: '实验预约管理',
    desc: '支持在线预约实验时间，自动检测资源冲突',
    icon: 'Histogram'
  },
  {
    title: '多维度管理控制',
    desc: '支持实验数据、教师、学生三级权限管理',
    icon: 'Setting'
  },
  {
    title: '实验记录追踪',
    desc: '完整记录实验数据，支持历史记录查询与追溯',
    icon: 'School'
  }
])

// 统计数据
const stats = ref([
  {
    title: '已预约实验数',
    value: 1285,
    unitText: '次',
    description: '已完成预约的实验总数',
    icon: 'Document'
  },
  {
    title: '设备利用率',
    value: 86.4,
    precision: 1,
    unitText: '%',
    description: '实验设备的平均使用率',
    icon: 'School'
  },
  {
    title: '冲突预警',
    value: 23,
    unitText: '次',
    description: '系统检测并预警的预约冲突次数',
    icon: 'Warning'
  },
  {
    title: '日均使用时长',
    value: 4.5,
    precision: 1,
    unitText: '小时',
    description: '每台设备的日均使用时长',
    icon: 'Stopwatch'
  }
])

// 示例课程数据
const getDemoCourses = (date) => {
  const courses = {
    '2023-09-18': [
      { time: '1-2', name: '高等数学', teacher: '王教授', room: 'A301', type: 0 },
      { time: '5-6', name: '大学英语', teacher: '李老师', room: 'B202', type: 1 }
    ],
    '2023-09-19': [
      { time: '3-4', name: '数据结构', teacher: '张教授', room: 'C401', type: 2 },
      { time: '7-8', name: '体育课', teacher: '陈教练', room: '操场', type: 3 }
    ]
  }
  return courses[date] || []
}

</script>

<style scoped>
.home-container {
  position: relative;
  background: linear-gradient(135deg, #f8faff 0%, #f0f5ff 100%);
  min-height: 100vh;
  overflow-x: hidden;
}

/* 装饰圆圈 */
.decorative-circle {
  position: fixed;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.15;
  z-index: 0;
}

.circle-1 {
  width: 400px;
  height: 400px;
  background: #409EFF;
  top: 15%;
  left: 5%;
}

.circle-2 {
  width: 350px;
  height: 350px;
  background: #67C23A;
  bottom: 10%;
  right: 5%;
}

.circle-3 {
  width: 300px;
  height: 300px;
  background: #E6A23C;
  top: 60%;
  left: 30%;
}

/* 导航栏样式 */
.nav-header {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  height: 64px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-content {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo-icon {
  font-size: 28px;
  color: #409EFF;
  margin-right: 10px;
}

.logo {
  color: #303133;
  font-size: 20px;
  font-weight: 600;
  margin: 0;
}

.nav-items {
  display: flex;
  gap: 10px;
}

.nav-link {
  font-size: 15px;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: 8px;
  transition: all 0.3s;
}

.nav-link:hover {
  background: rgba(64, 158, 255, 0.1);
  transform: translateY(-2px);
}

/* 主内容区域 */
.main-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
  position: relative;
  z-index: 1;
}

/* 轮播图模块 */
.carousel-section {
  margin-bottom: 40px;
}

.main-carousel {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
}

.carousel-content {
  height: 100%;
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  align-items: center;
  padding: 40px;
}

.carousel-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
}

.carousel-text {
  position: relative;
  color: white;
  max-width: 600px;
  text-align: left;
  animation: fadeIn 1s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.carousel-title {
  font-size: 42px;
  font-weight: 700;
  margin-bottom: 16px;
  line-height: 1.2;
}

.carousel-desc {
  font-size: 18px;
  margin-bottom: 24px;
  opacity: 0.9;
}

.carousel-btn {
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  background: linear-gradient(45deg, #409EFF, #66b1ff);
  border: none;
  transition: all 0.3s;
}

.carousel-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.4);
}

/* 欢迎模块 */
.hero-section {
  background: linear-gradient(135deg, #409EFF 0%, #337ecc 100%);
  color: white;
  padding: 60px 40px;
  text-align: center;
  border-radius: 12px;
  margin-bottom: 40px;
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.2);
}

.hero-title {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 16px;
  line-height: 1.3;
}

.hero-subtitle {
  font-size: 18px;
  margin-bottom: 32px;
  opacity: 0.9;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 16px;
}

.action-btn, .demo-btn {
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 8px;
}

.action-btn {
  background: white;
  color: #409EFF;
  border: none;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(255, 255, 255, 0.3);
}

.demo-btn {
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.4);
  color: white;
}

.demo-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
}

/* 标题样式 */
.section-title {
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 12px;
  text-align: center;
  background: linear-gradient(45deg, #409EFF, #67C23A);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.section-subtitle {
  font-size: 16px;
  color: #606266;
  text-align: center;
  margin-bottom: 30px;
}

/* 数据看板模块 */
.dashboard-section {
  margin-bottom: 60px;
}

.stat-card {
  border-radius: 12px;
  padding: 10px;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  transition: all 0.3s;
  background: rgba(255, 255, 255, 0.9);
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.stat-icon {
  margin-bottom: 16px;
}

.stat-value {
  margin-bottom: 8px;
}

.stat-desc {
  font-size: 14px;
  color: #909399;
  margin-top: 8px;
}

/* 核心功能模块 */
.core-features {
  margin-bottom: 60px;
}

.feature-card {
  text-align: center;
  margin-bottom: 30px;
  padding: 30px 20px;
  height: 100%;
  border-radius: 12px;
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: rgba(255, 255, 255, 0.9);
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 20px rgba(0, 0, 0, 0.08);
}

.feature-icon {
  margin-bottom: 20px;
}

.feature-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 16px;
  color: #303133;
}

.feature-desc {
  color: #606266;
  margin-bottom: 16px;
  line-height: 1.6;
}

.feature-btn {
  margin-top: auto;
  font-weight: 500;
}

/* 排课流程可视化 */
.process-section {
  margin-bottom: 60px;
}

.process-card {
  border-radius: 12px;
  padding: 30px;
  background: rgba(255, 255, 255, 0.9);
}

/* 课表预览 */
.timetable-preview {
  margin-bottom: 60px;
}

.calendar-card {
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.9);
}

.calendar-cell {
  min-height: 120px;
  padding: 5px;
}

.course-item {
  color: white;
  padding: 8px;
  margin: 4px 0;
  border-radius: 6px;
  font-size: 12px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.2s;
}

.course-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.course-title {
  font-weight: 500;
  margin-bottom: 4px;
}

.course-info {
  font-size: 11px;
  opacity: 0.9;
}

/* 底部信息 */
.main-footer {
  background: #2c3e50;
  color: white;
  padding: 60px 20px 20px;
  margin-top: 40px;
  height: auto;
}

.footer-content {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  gap: 40px;
  margin-bottom: 40px;
}

.footer-section {
  flex: 1;
  min-width: 250px;
}

.footer-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 20px;
  position: relative;
}

.footer-title:after {
  content: '';
  position: absolute;
  left: 0;
  bottom: -8px;
  width: 40px;
  height: 3px;
  background: #409EFF;
  border-radius: 2px;
}

.footer-text {
  line-height: 1.8;
  opacity: 0.8;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.social-icons {
  display: flex;
  gap: 12px;
}

.social-icon {
  background: rgba(255, 255, 255, 0.1);
  border: none;
  transition: all 0.3s;
}

.social-icon:hover {
  background: #409EFF;
  transform: translateY(-3px);
}

.copyright {
  max-width: 1400px;
  margin: 0 auto;
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  font-size: 14px;
  opacity: 0.6;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .carousel-title {
    font-size: 28px;
  }

  .carousel-desc {
    font-size: 14px;
  }

  .hero-title {
    font-size: 24px;
  }

  .hero-subtitle {
    font-size: 14px;
  }

  .action-buttons {
    flex-direction: column;
    gap: 10px;
  }

  .footer-content {
    flex-direction: column;
    gap: 30px;
  }
}
</style>
