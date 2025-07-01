<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工号" prop="teacherCode">
        <el-input
            v-model="queryParams.teacherCode"
            placeholder="请输入工号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师姓名" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入教师姓名"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['manage:teacher:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:teacher:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:teacher:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:teacher:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="teacherList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="教师唯一id" align="center" prop="id"/>
      <el-table-column label="工号" align="center" prop="teacherCode"/>
      <el-table-column label="教师姓名" align="center" prop="name"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:teacher:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:teacher:remove']">删除
          </el-button>
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

    <!-- 添加或修改教师管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="teacherRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工号" prop="teacherCode">
          <el-input v-model="form.teacherCode" placeholder="请输入工号"/>
        </el-form-item>
        <el-form-item label="教师姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入真实姓名"/>
        </el-form-item>
        <el-divider content-position="center">学生管理信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddStudent">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteStudent">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="studentList" :row-class-name="rowStudentIndex" @selection-change="handleStudentSelectionChange"
                  ref="student">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="学号" prop="studentCode" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.studentCode" placeholder="请输入学号"/>
            </template>
          </el-table-column>
          <el-table-column label="学生姓名" prop="studentName" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.studentName" placeholder="请输入学生姓名"/>
            </template>
          </el-table-column>
          <el-table-column label="实验预约时间" prop="experimentAppointmentTime" width="240">
            <template #default="scope">
              <el-date-picker clearable
                v-model="scope.row.experimentAppointmentTime"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择实验预约时间">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="备注信息" prop="remark" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注信息"/>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Teacher">
import {listTeacher, getTeacher, delTeacher, addTeacher, updateTeacher} from "@/api/manage/teacher";

const {proxy} = getCurrentInstance();

const teacherList = ref([]);
const studentList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedStudent = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    teacherCode: null,
    name: null,
  },
  rules: {
    teacherCode: [
      {required: true, message: "工号不能为空", trigger: "blur"}
    ],
    name: [
      {required: true, message: "真实姓名不能为空", trigger: "blur"}
    ],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询教师管理列表 */
function getList() {
  loading.value = true;
  listTeacher(queryParams.value).then(response => {
    teacherList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    teacherCode: null,
    name: null,
    createdTime: null
  };
  studentList.value = [];
  proxy.resetForm("teacherRef");
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
  title.value = "添加教师管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getTeacher(_id).then(response => {
    form.value = response.data;
    studentList.value = response.data.studentList;
    open.value = true;
    title.value = "修改教师管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["teacherRef"].validate(valid => {
    if (valid) {
      form.value.studentList = studentList.value;
      if (form.value.id != null) {
        updateTeacher(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTeacher(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除教师管理编号为"' + _ids + '"的数据项？').then(function () {
    return delTeacher(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 学生管理序号 */
function rowStudentIndex({row, rowIndex}) {
  row.index = rowIndex + 1;
}

/** 学生管理添加按钮操作 */
function handleAddStudent() {
  let obj = {};
  obj.studentCode = "";
  obj.studentName = "";
  obj.experimentAppointmentTime = "";
  obj.remark = "";
  studentList.value.push(obj);
}

/** 学生管理删除按钮操作 */
function handleDeleteStudent() {
  if (checkedStudent.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的学生管理数据");
  } else {
    const students = studentList.value;
    const checkedStudents = checkedStudent.value;
    studentList.value = students.filter(function (item) {
      return checkedStudents.indexOf(item.index) == -1
    });
  }
}

/** 复选框选中数据 */
function handleStudentSelectionChange(selection) {
  checkedStudent.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/teacher/export', {
    ...queryParams.value
  }, `teacher_${new Date().getTime()}.xlsx`)
}

getList();
</script>
