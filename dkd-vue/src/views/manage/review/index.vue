<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentCode">
        <el-input
            v-model="queryParams.studentCode"
            placeholder="请输入学号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
            v-model="queryParams.studentName"
            placeholder="请输入学生姓名"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>


      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学号" align="center" prop="studentCode" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="实验分数" align="center" prop="experimentScore">
<!--        <template #default="scope">-->
<!--          <span v-if="scope.row.experimentScore">{{ scope.row.experimentScore }}</span>-->
<!--          <span v-else>未评分</span>-->
<!--        </template>-->
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleReview(scope.row)">查看实验内容</el-button>
          <el-button link type="primary" icon="Edit" @click="handleMark(scope.row)">评分</el-button>
          <br>
          <el-button link type="success" icon="data-analysis" @click="handleAIAnalysis(scope.row)">AI实验行为分析</el-button>
          <!-- 新增AI评分按钮 -->
          <el-button link type="warning" icon="star" @click="handleAIScore(scope.row)">AI评分</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 评分对话框 -->
    <el-dialog :title="title" v-model="mark" width="500px" append-to-body>
      <el-form ref="experiment1Ref" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生ID" prop="studentId">
          <el-input v-model="form.studentId" :disabled="true" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentId">
          <el-input v-model="form.studentName" :disabled="true" />
        </el-form-item>
        <el-form-item label="实验成绩" prop="experimentScore">
          <el-input v-model="form.experimentScore" placeholder="请输入实验成绩" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 新增的 AI实验行为分析 弹窗 -->
<!--    <el-dialog :title="aiTitle" v-model="aiDialogVisible" width="500px" append-to-body>-->
<!--      <p>{{ aiMessage }}</p>-->
<!--      <template #footer>-->
<!--        <div class="dialog-footer">-->
<!--          <el-button @click="aiDialogVisible = false">关 闭</el-button>-->
<!--        </div>-->
<!--      </template>-->
<!--    </el-dialog>-->

<!--    <el-dialog :title="aiTitle" v-model="aiDialogVisible" width="600px" append-to-body>-->
<!--      <div style="white-space: pre-wrap;">{{ aiMessage }}</div>-->
<!--      <template #footer>-->
<!--        <div class="dialog-footer">-->
<!--          <el-button @click="aiDialogVisible = false">关 闭</el-button>-->
<!--        </div>-->
<!--      </template>-->
<!--    </el-dialog>-->

    <el-dialog :title="aiTitle" v-model="aiDialogVisible" width="600px" append-to-body :close-on-click-modal="false" class="ai-analysis-dialog">

      <div style="min-height: 120px;">
        <!-- 加载状态 -->
        <div v-if="aiLoading" style="text-align: center; padding: 20px;">
          <el-skeleton :rows="3" animated />
          <p style="color: #666; margin-top: 10px;">AI分析中，请稍候...</p>
          <el-progress
              v-if="analysisProgress >= 0"
              :percentage="analysisProgress"
              :indeterminate="true"
              :duration="0.5" />
        </div>

        <!-- 分析结果展示 -->
        <div v-if="!aiLoading" class="analysis-result-container">
          <!-- 使用el-card增强视觉效果 -->
          <el-card class="analysis-result-card">
            <div class="analysis-text" v-html="formattedAnalysisText"></div>

            <!-- 添加评分组件 -->
<!--            <div v-if="probabilityRate > 0" class="rate-section">-->
<!--              <el-rate-->
<!--                  v-model="probabilityRate"-->
<!--                  :disabled="true"-->
<!--                  :allow-half="true"-->
<!--                  :max="100"-->
<!--                  :colors="['#f56c6c', '#e6a23c', '#51a3a3']"-->
<!--                  :void-color="'#dcdfe6'"-->
<!--                  :score-template="`${probabilityRate}`"        style="width: 100%; max-width: 550px;">-->
<!--              </el-rate>-->

<!--              &lt;!&ndash; 评分说明 &ndash;&gt;-->
<!--              <div class="rate-legend">-->
<!--                <span>低风险</span>-->
<!--                <span>中等风险</span>-->
<!--                <span>高风险</span>-->
<!--              </div>-->
<!--            </div>-->

            <!-- 分析耗时显示 -->
            <div v-if="analysisDuration > 0" class="analysis-duration">
              <i class="el-icon-time"></i> 分析耗时：{{ analysisDuration /1000}}秒
            </div>
          </el-card>
        </div>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="aiDialogVisible = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 新增的 AI评分 弹窗 -->
    <el-dialog :title="aiscoreTitle" v-model="aiscoreDialogVisible" width="600px" append-to-body :close-on-click-modal="false" class="ai-score-dialog">
      <div style="min-height: 120px;">
        <!-- 加载状态 -->
        <div v-if="aiscoreLoading" style="text-align: center; padding: 20px;">
          <el-skeleton :rows="3" animated />
          <p style="color: #666; margin-top: 10px;">AI评分中，请稍候...</p>
          <el-progress
              v-if="scoreProgress >= 0"
              :percentage="scoreProgress"
              :indeterminate="true"
              :duration="0.5" />
        </div>

        <!-- 评分结果展示 -->
        <div v-if="!aiscoreLoading" class="score-result-container">
          <el-card class="score-result-card">
            <div class="score-text" v-html="formattedAIScoreText"></div>

            <!-- 评分详情展示 -->
            <div class="score-details">
              <el-row :gutter="20">
                <el-col :span="8">
                  <div class="score-item accuracy">
                    <div class="score-label">数据准确性</div>
                    <div class="score-value">{{ aiscoreData.accuracyScore }}</div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="score-item wave">
                    <div class="score-label">波形匹配度</div>
                    <div class="score-value">{{ aiscoreData.waveScore }}</div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="score-item operation">
                    <div class="score-label">操作规范性</div>
                    <div class="score-value">{{ aiscoreData.operationScore }}</div>
                  </div>
                </el-col>
              </el-row>

              <!-- 总评 -->
              <div class="total-score">
                <span class="total-label">总评分：</span>
                <span class="total-value">{{ aiscoreData.score }}</span>
              </div>
            </div>

            <!-- 评语展示 -->
            <div class="evaluation-text">
              <div class="evaluation-title">评分评语：</div>
              <div class="evaluation-content">{{ aiscoreData.evaluation }}</div>
            </div>

            <!-- 分析耗时显示 -->
            <div v-if="scoreDuration > 0" class="score-duration">
              <i class="el-icon-time"></i> 分析耗时：{{ scoreDuration /1000}}秒
            </div>
          </el-card>
        </div>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="aiscoreDialogVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Student">
import { listStudent, getStudent, delStudent, addStudent, updateStudent } from "@/api/manage/student";
import {
  getExperiment1,
  listExperiment1,
  updateExperiment1,
  getAIAnalysis,
  getExperimentScore
} from "../../../api/manage/experiment1";

const { proxy } = getCurrentInstance();
const { current_status } = proxy.useDict('current_status');

const studentList = ref([]);
const experiment1List = ref([]);
//const getAIAnalysis = ref([]);
const mark = ref(false);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedExperiment1 = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const aiDialogVisible = ref(false);
const aiTitle = ref("AI实验行为分析");
const aiLoading = ref(false);
const aiMessage = ref("");
const analysisDuration = ref(0);
const analysisProgress = ref(0);
const retryCount = ref(0);
// 新增评分组件相关变量
const probabilityRate = ref(0);
const res = ref(null); // 用于控制组件显示

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    studentCode: null,
    studentName: null,
    teacherId: null,
    experimentAppointmentTime: null,
  },
  rules: {
    studentCode: [
      { required: true, message: "学号，唯一标识不能为空", trigger: "blur" }
    ],
    studentName: [
      { required: true, message: "学生姓名不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

const formattedAnalysisText = computed(() => {
  if (!aiMessage.value) return '';

  // 添加风险等级判断
  let riskLevel = 'low';
  if (probabilityRate.value >= 70) riskLevel = 'high';
  else if (probabilityRate.value >= 30) riskLevel = 'medium';

  // 返回带数据属性的HTML
  return `<div data-risk="${riskLevel}">${aiMessage.value}</div>`;
});

/** 查询学生管理列表 */
function getList() {
  loading.value = true;
  listStudent(queryParams.value).then(response => {
    studentList.value = response.rows;
    total.value = response.total;

    // 使用学生ID列表查询实验成绩
    if (response.rows.length > 0) {
      const studentIds = response.rows.map(student => student.id);
      listExperiment1({ ...queryParams.value, studentIds }).then(experimentResponse => {
        // 创建成绩映射表
        const scoreMap = {};
        experimentResponse.rows.forEach(experiment => {
          scoreMap[experiment.studentId] = experiment.experimentScore;
        });

        // 将成绩合并到学生列表
        studentList.value = response.rows.map(student => ({
          ...student,
          experimentScore: scoreMap[student.id] || '未评分'
        }));

        loading.value = false;
      });
    } else {
      loading.value = false;
    }
  });
}

/** AI实验行为分析按钮操作 */
// function handleAIAnalysis(row) {
//   // 可以根据row中的数据做更复杂的逻辑判断
//   aiDialogVisible.value = true;
// }
/** AI实验行为分析按钮操作 */
// function handleAIAnalysis(row) {
//   // 调用AI分析接口
//   getAIAnalysis(row.id).then(response => {
//     aiMessage.value = `检测结果：${response.data.analysis}\n作弊概率：${response.data.probability.toFixed(2)}%`;
//     aiDialogVisible.value = true;
//   }).catch(error => {
//     proxy.$modal.msgError("AI分析失败：" + error.message);
//   });
// }
function handleAIAnalysis(row) {
  // 重置状态
  aiDialogVisible.value = true;
  aiLoading.value = true;
  aiMessage.value = "";
  analysisDuration.value = 0;
  analysisProgress.value = 0;
  retryCount.value = 0;

  // 启动进度条动画
  startProgressAnimation();

  // 记录开始时间
  const startTime = Date.now();

  // 定义带重试机制的获取方法
  function fetchWithRetry(id, maxRetries = 3) {
    return getAIAnalysis(id).catch(error => {
      if (retryCount.value < maxRetries) {
        retryCount.value++;
        return fetchWithRetry(id, maxRetries);
      }
      throw error;
    });
  }

  // 执行API调用
  fetchWithRetry(row.id)
      .then(response => {

        // 保存原始响应数据
        res.value = response;

        // 格式化数据显示
        const formattedProbability = parseFloat(response.data.probability).toFixed(2);

        // 更新分析结果
        aiMessage.value = `检测结果：${response.data.analysis}\n\n`;
        aiMessage.value += `作弊概率：${formattedProbability}%`;

        // 更新评分值
        probabilityRate.value = response.data.probability;
        // 记录耗时
        analysisDuration.value = Date.now() - startTime;
      })
      .catch(error => {
        // 错误处理
        aiMessage.value = `分析失败：${error.message}`;
        proxy.$modal.msgError(`AI分析失败（尝试次数：${retryCount.value}次）：${error.message}`);
      })
      .finally(() => {
        // 停止进度条
        stopProgressAnimation();

        // 最终状态更新
        aiLoading.value = false;
      });
}


// 进度条动画控制
let progressInterval = null;
function startProgressAnimation() {
  analysisProgress.value = 0;
  progressInterval = setInterval(() => {
    if (analysisProgress.value < 80) {
      analysisProgress.value += 5;
    }
  }, 200);
}


function stopProgressAnimation() {
  clearInterval(progressInterval);
  analysisProgress.value = 100;
}


// 在script setup中暴露方法
defineExpose({
  handleAIAnalysis,
  handleAIScore // 暴露新增的方法
});

// 取消按钮
function cancel() {
  mark.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    studentCode: null,
    studentName: null,
    teacherId: null,
    experimentAppointmentTime: null,
    createTime: null,
    remark: null
  };
  experiment1List.value = [];
  proxy.resetForm("studentRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加学生管理";
}

/** 评分按钮操作 */
function handleMark(row) {
  reset();
  const _id = row.id || ids.value
  getExperiment1(_id).then(response => {
    form.value = response.data;
    getStudent(_id).then(response => {
      form.value.studentName = response.data.studentName;
      mark.value = true;
      title.value = "评分";
    });

  });
}

/** 提交按钮 */
function submitForm() {
  updateExperiment1(form.value).then(response => {
    proxy.$modal.msgSuccess("评分成功");
    mark.value = false;
    getList();
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除学生管理编号为"' + _ids + '"的数据项？').then(function() {
    return delStudent(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 实验数据管理序号 */
function rowExperiment1Index({ row, rowIndex }) {
  row.index = rowIndex + 1;
}

/** 实验数据管理添加按钮操作 */
function handleAddExperiment1() {
  let obj = {};
  experiment1List.value.push(obj);
}

/** 实验数据管理删除按钮操作 */
function handleDeleteExperiment1() {
  if (checkedExperiment1.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的实验数据管理数据");
  } else {
    const experiment1s = experiment1List.value;
    const checkedExperiment1s = checkedExperiment1.value;
    experiment1List.value = experiment1s.filter(function(item) {
      return checkedExperiment1s.indexOf(item.index) == -1
    });
  }
}

/** 复选框选中数据 */
function handleExperiment1SelectionChange(selection) {
  checkedExperiment1.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/student/export', {
    ...queryParams.value
  }, `student_${new Date().getTime()}.xlsx`)
}

/** 评阅按钮操作 */
function handleReview(row) {
  // 获取学生ID
  const studentId = row.studentCode;
  // 跳转到实验数据页面并携带学生ID
  proxy.$router.push({ path: 'review/tables', query: { studentId: studentId } });
}

// 新增AI评分相关变量
const aiscoreDialogVisible = ref(false);
const aiscoreTitle = ref("AI评分结果");
const aiscoreLoading = ref(false);
const aiscoreData = ref({});
const scoreDuration = ref(0);
const scoreProgress = ref(0);
const retryScoreCount = ref(0);

// 评分结果格式化计算属性
const formattedAIScoreText = computed(() => {
  if (!aiscoreData.value.evaluation) return '';

  // 添加评分等级判断
  let scoreLevel = 'low';
  if (aiscoreData.value.score >= 85) scoreLevel = 'high';
  else if (aiscoreData.value.score >= 60) scoreLevel = 'medium';

  // 返回带数据属性的HTML
  return `<div data-score="${scoreLevel}">总评分：${aiscoreData.value.score}</div>`;
});

/** AI评分按钮操作 */
function handleAIScore(row) {
  // 重置状态
  aiscoreDialogVisible.value = true;
  aiscoreLoading.value = true;
  aiscoreData.value = {};
  scoreDuration.value = 0;
  scoreProgress.value = 0;
  retryScoreCount.value = 0;

  // 启动进度条动画
  startScoreAnimation();

  // 记录开始时间
  const startTime = Date.now();

  // 定义带重试机制的获取方法
  function fetchScoreWithRetry(id, maxRetries = 3) {
    return getExperimentScore(id).catch(error => {
      if (retryScoreCount.value < maxRetries) {
        retryScoreCount.value++;
        return fetchScoreWithRetry(id, maxRetries);
      }
      throw error;
    });
  }

  // 执行API调用
  fetchScoreWithRetry(row.id)
      .then(response => {
        // 保存评分数据
        aiscoreData.value = response.data;

        // 记录耗时
        scoreDuration.value = Date.now() - startTime;
      })
      .catch(error => {
        // 错误处理
        aiscoreData.value = {
          score: 0,
          evaluation: `评分失败：${error.message}`
        };
        proxy.$modal.msgError(`AI评分失败（尝试次数：${retryScoreCount.value}次）：${error.message}`);
      })
      .finally(() => {
        // 停止进度条
        stopScoreAnimation();

        // 最终状态更新
        aiscoreLoading.value = false;
      });
}

// 评分进度条动画控制
let scoreInterval = null;
function startScoreAnimation() {
  scoreProgress.value = 0;
  scoreInterval = setInterval(() => {
    if (scoreProgress.value < 80) {
      scoreProgress.value += 5;
    }
  }, 200);
}

function stopScoreAnimation() {
  clearInterval(scoreInterval);
  scoreProgress.value = 100;
}


getList();
</script>

<style scoped>
/* 分析结果容器样式 */
.analysis-result-container {
  margin: 20px 0;
  max-width: 90%;
  margin-left: auto;
  margin-right: auto;
}

/* 卡片样式 */
.analysis-result-card {
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  background-color: #f9f9f9;
}

/* 分析文本样式 */
.analysis-text {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
  padding: 15px;
  white-space: pre-wrap;
  border-left: 5px solid var(--risk-color);
  background-color: var(--risk-bg-color);
  margin-bottom: 15px;
}

/* 评分组件样式 */
.rate-section {
  margin: 20px 0;
  text-align: center;
}

/* 评分说明样式 */
.rate-legend {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #999;
  margin-top: 8px;
}

/* 分析耗时样式 */
.analysis-duration {
  margin-top: 15px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 5px;
  text-align: right;
  color: #666;
  font-size: 13px;
}

/* 风险等级样式 */
.analysis-text[data-risk="high"] {
  --risk-color: #f56c6c;
  --risk-bg-color: #fef0f0;
}

.analysis-text[data-risk="medium"] {
  --risk-color: #e6a23c;
  --risk-bg-color: #fdf6ec;
}

.analysis-text[data-risk="low"] {
  --risk-color: #51a3a3;
  --risk-bg-color: #f0f9f0;
}

/* 评分结果样式 */
.score-result-container {
  margin: 20px 0;
  max-width: 90%;
  margin-left: auto;
  margin-right: auto;
}

.score-result-card {
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  background-color: #f9f9f9;
}

.score-text {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
  padding: 15px;
  white-space: pre-wrap;
  border-left: 5px solid var(--score-color);
  background-color: var(--score-bg-color);
  margin-bottom: 15px;
}

/* 评分详情样式 */
.score-details {
  margin: 20px 0;
}

.score-item {
  text-align: center;
  padding: 15px;
  border-radius: 8px;
  background-color: #f5f7fa;
}

.score-label {
  font-weight: bold;
  margin-bottom: 8px;
}

.score-value {
  font-size: 20px;
  color: #409EFF;
}

.total-score {
  margin-top: 20px;
  padding: 15px;
  background-color: #ecf5ff;
  border-radius: 6px;
  text-align: right;
}

.total-label {
  font-weight: bold;
  margin-right: 10px;
}

.total-value {
  font-size: 22px;
  color: #1890ff;
}

/* 评语样式 */
.evaluation-text {
  margin-top: 20px;
  padding: 15px;
  background-color: #fcf9e8;
  border-radius: 6px;
}

.evaluation-title {
  font-weight: bold;
  margin-bottom: 8px;
  display: block;
}

.evaluation-content {
  white-space: pre-wrap;
  line-height: 1.5;
}

/* 评分耗时样式 */
.score-duration {
  margin-top: 15px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 5px;
  text-align: right;
  color: #666;
  font-size: 13px;
}

/* 评分等级样式 */
.score-text[data-score="high"] {
  --score-color: #409EFF;
  --score-bg-color: #ecf5ff;
}

.score-text[data-score="medium"] {
  --score-color: #13ce66;
  --score-bg-color: #f0f9eb;
}

.score-text[data-score="low"] {
  --score-color: #FF9900;
  --score-bg-color: #fffbe6;
}

/* 评分项颜色覆盖 */
.accuracy .score-value {
  color: #409EFF;
}

.wave .score-value {
  color: #13ce66;
}

.operation .score-value {
  color: #FF9900;
}
</style>

