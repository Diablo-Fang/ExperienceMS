<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生ID" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学生ID"
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
          v-hasPermi="['manage:experiment1:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:experiment1:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:experiment1:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:experiment1:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="experiment1List" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="实验数据ID" align="center" prop="id" />
      <el-table-column label="学生ID" align="center" prop="studentId" />
      <el-table-column label="实验成绩" align="center" prop="experimentScore" />
      <el-table-column label="实验进度" align="center" prop="progress" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:experiment1:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:experiment1:remove']">删除</el-button>
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

    <!-- 添加或修改实验数据管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="experiment1Ref" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生ID" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学生ID" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改实验数据管理对话框 -->
    <el-dialog :title="title" v-model="update" width="500px" append-to-body>
      <el-form ref="experiment1Ref" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生ID" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学生ID" :disabled="true" />
        </el-form-item>
        <el-form-item label="表1-第一级-VC" prop="table1vc1Field">
          <el-input v-model="form.table1vc1Field" placeholder="请输入表1-第一级-VC" />
        </el-form-item>
        <el-form-item label="表1-第一级-VB" prop="table1vb1Field">
          <el-input v-model="form.table1vb1Field" placeholder="请输入表1-第一级-VB" />
        </el-form-item>
        <el-form-item label="表1-第一级-VE" prop="table1ve1Field">
          <el-input v-model="form.table1ve1Field" placeholder="请输入表1-第一级-VE" />
        </el-form-item>
        <el-form-item label="表1-第二级-VC" prop="table1vc2Field">
          <el-input v-model="form.table1vc2Field" placeholder="请输入表1-第二级-VC" />
        </el-form-item>
        <el-form-item label="表1-第二级-VB" prop="table1vb2Field">
          <el-input v-model="form.table1vb2Field" placeholder="请输入表1-第二级-VB" />
        </el-form-item>
        <el-form-item label="表1-第二级-VE" prop="table1ve2Field">
          <el-input v-model="form.table1ve2Field" placeholder="请输入表1-第二级-VE" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Vs" prop="table2vs1Field">
          <el-input v-model="form.table2vs1Field" placeholder="请输入表2-基本放大-Vs" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Vol" prop="table2vol1Field">
          <el-input v-model="form.table2vol1Field" placeholder="请输入表2-基本放大-Vol" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Avl" prop="table2avl1Field">
          <el-input v-model="form.table2avl1Field" placeholder="请输入表2-基本放大-Avl" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Vo" prop="table2vo1Field">
          <el-input v-model="form.table2vo1Field" placeholder="请输入表2-基本放大-Vo" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Av" prop="table2av1Field">
          <el-input v-model="form.table2av1Field" placeholder="请输入表2-基本放大-Av" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Ri" prop="table2ri1Field">
          <el-input v-model="form.table2ri1Field" placeholder="请输入表2-基本放大-Ri" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Ro" prop="table2ro1Field">
          <el-input v-model="form.table2ro1Field" placeholder="请输入表2-基本放大-Ro" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Vo(10V)" prop="table2vo3Field">
          <el-input v-model="form.table2vo3Field" placeholder="请输入表2-基本放大-Vo(10V)" />
        </el-form-item>
        <el-form-item label="表2-基本放大-Av(10V)" prop="table2av3Field">
          <el-input v-model="form.table2av3Field" placeholder="请输入表2-基本放大-Av(10V)" />
        </el-form-item>
        <el-form-item label="表2-基本放大-W" prop="table2w1Field">
          <el-input v-model="form.table2w1Field" placeholder="请输入表2-基本放大-W" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Vs" prop="table2vs2Field">
          <el-input v-model="form.table2vs2Field" placeholder="请输入表2-负反馈放大-Vs" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Vol" prop="table2vol2Field">
          <el-input v-model="form.table2vol2Field" placeholder="请输入表2-负反馈放大-Vol" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Avl" prop="table2avl2Field">
          <el-input v-model="form.table2avl2Field" placeholder="请输入表2-负反馈放大-Avl" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Vo" prop="table2vo2Field">
          <el-input v-model="form.table2vo2Field" placeholder="请输入表2-负反馈放大-Vo" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Av" prop="table2av2Field">
          <el-input v-model="form.table2av2Field" placeholder="请输入表2-负反馈放大-Av" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Ri" prop="table2ri2Field">
          <el-input v-model="form.table2ri2Field" placeholder="请输入表2-负反馈放大-Ri" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Ro" prop="table2ro2Field">
          <el-input v-model="form.table2ro2Field" placeholder="请输入表2-负反馈放大-Ro" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Vo(10V)" prop="table2vo4Field">
          <el-input v-model="form.table2vo4Field" placeholder="请输入表2-负反馈放大-Vo(10V)" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-Av(10V)" prop="table2av4Field">
          <el-input v-model="form.table2av4Field" placeholder="请输入表2-负反馈放大-Av(10V)" />
        </el-form-item>
        <el-form-item label="表2-负反馈放大-W" prop="table2w2Field">
          <el-input v-model="form.table2w2Field" placeholder="请输入表2-负反馈放大-W" />
        </el-form-item>
        <el-form-item label="表3-典型差放-VC1" prop="table3vc11Field">
          <el-input v-model="form.table3vc11Field" placeholder="请输入表3-典型差放-VC1" />
        </el-form-item>
        <el-form-item label="表3-典型差放-VC2" prop="table3vc21Field">
          <el-input v-model="form.table3vc21Field" placeholder="请输入表3-典型差放-VC2" />
        </el-form-item>
        <el-form-item label="表3-典型差放-VB1" prop="table3vb11Field">
          <el-input v-model="form.table3vb11Field" placeholder="请输入表3-典型差放-VB1" />
        </el-form-item>
        <el-form-item label="表3-典型差放-VB2" prop="table3vb21Field">
          <el-input v-model="form.table3vb21Field" placeholder="请输入表3-典型差放-VB2" />
        </el-form-item>
        <el-form-item label="表3-典型差放-VE1" prop="table3ve11Field">
          <el-input v-model="form.table3ve11Field" placeholder="请输入表3-典型差放-VE1" />
        </el-form-item>
        <el-form-item label="表3-典型差放-VE2" prop="table3ve21Field">
          <el-input v-model="form.table3ve21Field" placeholder="请输入表3-典型差放-VE2" />
        </el-form-item>
        <el-form-item label="表3-恒流源差放-VC1" prop="table3vc12Field">
          <el-input v-model="form.table3vc12Field" placeholder="请输入表3-恒流源差放-VC1" />
        </el-form-item>
        <el-form-item label="表3-恒流源差放-VC2" prop="table3vc22Field">
          <el-input v-model="form.table3vc22Field" placeholder="请输入表3-恒流源差放-VC2" />
        </el-form-item>
        <el-form-item label="表3-恒流源差放-VB1" prop="table3vb12Field">
          <el-input v-model="form.table3vb12Field" placeholder="请输入表3-恒流源差放-VB1" />
        </el-form-item>
        <el-form-item label="表3-恒流源差放-VB2" prop="table3vb22Field">
          <el-input v-model="form.table3vb22Field" placeholder="请输入表3-恒流源差放-VB2" />
        </el-form-item>
        <el-form-item label="表3-恒流源差放-VE1" prop="table3ve12Field">
          <el-input v-model="form.table3ve12Field" placeholder="请输入表3-恒流源差放-VE1" />
        </el-form-item>
        <el-form-item label="表3-恒流源差放-VE2" prop="table3ve22Field">
          <el-input v-model="form.table3ve22Field" placeholder="请输入表3-恒流源差放-VE2" />
        </el-form-item>
        <el-form-item label="表4-典型差放差模-VO1" prop="table4vo11Field">
          <el-input v-model="form.table4vo11Field" placeholder="请输入表4-典型差放差模-VO1" />
        </el-form-item>
        <el-form-item label="表4-典型差放差模-VO2" prop="table4vo21Field">
          <el-input v-model="form.table4vo21Field" placeholder="请输入表4-典型差放差模-VO2" />
        </el-form-item>
        <el-form-item label="表4-典型差放差模-VO" prop="table4vo1Field">
          <el-input v-model="form.table4vo1Field" placeholder="请输入表4-典型差放差模-VO" />
        </el-form-item>
        <el-form-item label="表4-典型差放差模-增益" prop="table4a1Field">
          <el-input v-model="form.table4a1Field" placeholder="请输入表4-典型差放差模-增益" />
        </el-form-item>
        <el-form-item label="表4-典型差放差模-KCMR" prop="table4k1Field">
          <el-input v-model="form.table4k1Field" placeholder="请输入表4-典型差放差模-KCMR" />
        </el-form-item>
        <el-form-item label="表4-典型差放共模-VO1" prop="table4vo12Field">
          <el-input v-model="form.table4vo12Field" placeholder="请输入表4-典型差放共模-VO1" />
        </el-form-item>
        <el-form-item label="表4-典型差放共模-VO2" prop="table4vo22Field">
          <el-input v-model="form.table4vo22Field" placeholder="请输入表4-典型差放共模-VO2" />
        </el-form-item>
        <el-form-item label="表4-典型差放共模-VO" prop="table4vo2Field">
          <el-input v-model="form.table4vo2Field" placeholder="请输入表4-典型差放共模-VO" />
        </el-form-item>
        <el-form-item label="表4-典型差放共模-增益" prop="table4a2Field">
          <el-input v-model="form.table4a2Field" placeholder="请输入表4-典型差放共模-增益" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放差模-VO1" prop="table4vo13Field">
          <el-input v-model="form.table4vo13Field" placeholder="请输入表4-恒流源差放差模-VO1" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放差模-VO2" prop="table4vo23Field">
          <el-input v-model="form.table4vo23Field" placeholder="请输入表4-恒流源差放差模-VO2" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放差模-VO" prop="table4vo3Field">
          <el-input v-model="form.table4vo3Field" placeholder="请输入表4-恒流源差放差模-VO" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放差模-增益" prop="table4a3Field">
          <el-input v-model="form.table4a3Field" placeholder="请输入表4-恒流源差放差模-增益" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放差模-KCMR" prop="table4k2Field">
          <el-input v-model="form.table4k2Field" placeholder="请输入表4-恒流源差放差模-KCMR" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放共模-VO1" prop="table4vo14Field">
          <el-input v-model="form.table4vo14Field" placeholder="请输入表4-恒流源差放共模-VO1" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放共模-VO2" prop="table4vo24Field">
          <el-input v-model="form.table4vo24Field" placeholder="请输入表4-恒流源差放共模-VO2" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放共模-VO" prop="table4vo4Field">
          <el-input v-model="form.table4vo4Field" placeholder="请输入表4-恒流源差放共模-VO" />
        </el-form-item>
        <el-form-item label="表4-恒流源差放共模-增益" prop="table4a4Field">
          <el-input v-model="form.table4a4Field" placeholder="请输入表4-恒流源差放共模-增益" />
        </el-form-item>
        <el-form-item label="表5-反向比例-Vo1" prop="table5vi1Field">
          <el-input v-model="form.table5vi1Field" placeholder="请输入表5-反向比例-Vo1" />
        </el-form-item>
        <el-form-item label="表5-反向比例-运放序号1" prop="table5vo1Field">
          <el-input v-model="form.table5vo1Field" placeholder="请输入表5-反向比例-运放序号1" />
        </el-form-item>
        <el-form-item label="表5-反向比例-Vo2" prop="table5vi2Field">
          <el-input v-model="form.table5vi2Field" placeholder="请输入表5-反向比例-Vo2" />
        </el-form-item>
        <el-form-item label="表5-反向比例-运放序号2" prop="table5vo2Field">
          <el-input v-model="form.table5vo2Field" placeholder="请输入表5-反向比例-运放序号2" />
        </el-form-item>
        <el-form-item label="万用表档位，枚举类型" prop="currentText">
          <el-select v-model="form.currentText" placeholder="请选择万用表档位，枚举类型">
            <el-option
                v-for="dict in current_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="vi1vo1图像路径" prop="vi1vo1Image">
          <image-upload v-model="form.vi1vo1Image"/>
        </el-form-item>
        <el-form-item label="vi1vo2图像路径" prop="vi1vo2Image">
          <image-upload v-model="form.vi1vo2Image"/>
        </el-form-item>
        <el-form-item label="vo1vo2图像路径" prop="vo1vo2Image">
          <image-upload v-model="form.vo1vo2Image"/>
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
  </div>
</template>

<script setup name="Experiment1">
import { listExperiment1, getExperiment1, delExperiment1, addExperiment1, updateExperiment1 } from "@/api/manage/experiment1";

const { proxy } = getCurrentInstance();
const { current_status } = proxy.useDict('current_status');

const experiment1List = ref([]);
const open = ref(false);
const update = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    studentId: null,
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询实验数据管理列表 */
function getList() {
  loading.value = true;
  listExperiment1(queryParams.value).then(response => {
    experiment1List.value = response.rows;
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
    studentId: null,
    table1vc1Field: null,
    table1vb1Field: null,
    table1ve1Field: null,
    table1vc2Field: null,
    table1vb2Field: null,
    table1ve2Field: null,
    table2vs1Field: null,
    table2vol1Field: null,
    table2avl1Field: null,
    table2vo1Field: null,
    table2av1Field: null,
    table2ri1Field: null,
    table2ro1Field: null,
    table2vo3Field: null,
    table2av3Field: null,
    table2w1Field: null,
    table2vs2Field: null,
    table2vol2Field: null,
    table2avl2Field: null,
    table2vo2Field: null,
    table2av2Field: null,
    table2ri2Field: null,
    table2ro2Field: null,
    table2vo4Field: null,
    table2av4Field: null,
    table2w2Field: null,
    table3vc11Field: null,
    table3vc21Field: null,
    table3vb11Field: null,
    table3vb21Field: null,
    table3ve11Field: null,
    table3ve21Field: null,
    table3vc12Field: null,
    table3vc22Field: null,
    table3vb12Field: null,
    table3vb22Field: null,
    table3ve12Field: null,
    table3ve22Field: null,
    table4vo11Field: null,
    table4vo21Field: null,
    table4vo1Field: null,
    table4a1Field: null,
    table4k1Field: null,
    table4vo12Field: null,
    table4vo22Field: null,
    table4vo2Field: null,
    table4a2Field: null,
    table4vo13Field: null,
    table4vo23Field: null,
    table4vo3Field: null,
    table4a3Field: null,
    table4k2Field: null,
    table4vo14Field: null,
    table4vo24Field: null,
    table4vo4Field: null,
    table4a4Field: null,
    table5vi1Field: null,
    table5vo1Field: null,
    table5vi2Field: null,
    table5vo2Field: null,
    currentText: null,
    vi1vo1Image: null,
    updateTime: null,
    vi1vo2Image: null,
    vo1vo2Image: null,
    experimentScore: null,
    createTime: null,
    progress: null
  };
  proxy.resetForm("experiment1Ref");
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
  title.value = "添加实验数据管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getExperiment1(_id).then(response => {
    form.value = response.data;
    update.value = true;
    title.value = "修改实验数据管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["experiment1Ref"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateExperiment1(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          update.value = false;
          getList();
        });
      } else {
        addExperiment1(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除实验数据管理编号为"' + _ids + '"的数据项？').then(function() {
    return delExperiment1(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/experiment1/export', {
    ...queryParams.value
  }, `experiment1_${new Date().getTime()}.xlsx`)
}

getList();
</script>
