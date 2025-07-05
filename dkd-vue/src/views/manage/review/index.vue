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
          <el-button link type="success" icon="data-analysis" @click="handleAIAnalysis(scope.row)">AI实验行为分析</el-button>
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
    <el-dialog :title="aiTitle" v-model="aiDialogVisible" width="500px" append-to-body>
      <p>{{ aiMessage }}</p>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="aiDialogVisible = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script setup name="Student">
import { listStudent, getStudent, delStudent, addStudent, updateStudent } from "@/api/manage/student";
import {getExperiment1, listExperiment1, updateExperiment1} from "../../../api/manage/experiment1";

const { proxy } = getCurrentInstance();
const { current_status } = proxy.useDict('current_status');

const studentList = ref([]);
const experiment1List = ref([]);
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
const aiMessage = ref("该学生高度异常，完成报告的时间短得极其反常，远远低于完成一个有效实验所需的最短合理时间。");


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

// 取消按钮
function cancel() {
  mark.value = false;
  reset();
}

/** AI实验行为分析按钮操作 */
function handleAIAnalysis(row) {
  // 可以根据row中的数据做更复杂的逻辑判断
  aiDialogVisible.value = true;
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

getList();
</script>
