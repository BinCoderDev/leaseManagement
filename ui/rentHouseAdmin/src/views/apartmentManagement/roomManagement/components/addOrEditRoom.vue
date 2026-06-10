<template>
  <el-card>
    <template #header>
      <div class="card-header">
        <span>{{ formData.id ? '修改' : '新增' }}房间</span>
      </div>
    </template>
    <el-form
      ref="apartmentFormRef"
      :model="formData"
      :rules="rules"
      label-width="120px"
      style="max-width: 660px"
      status-icon
    >
      <el-form-item label="房间号" prop="roomNumber">
        <el-input v-model="formData.roomNumber" />
      </el-form-item>
      <el-form-item label="租金（元/月）" prop="rent">
        <el-input-number v-model="formData.rent" :step="1" step-strictly />
      </el-form-item>
      <el-form-item label="所属公寓" required>
        <div class="flex-center">
          <el-form-item prop="provinceId">
            <el-select
              v-model.number="areaInfo.provinceId"
              placeholder="请选择省份"
              clearable
              @change="provinceChangeCallback"
              @clear="provinceClearCallback"
              style="width: 200px;"
            >
              <el-option
                v-for="item in areaInfo.provinceList"
                :key="item.id"
                :label="item.name"
                :value="Number(item.id)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="cityId">
            <el-select
              v-model.number="areaInfo.cityId"
              placeholder="请选择城市"
              clearable
              @change="cityChangeCallback"
              @clear="cityClearCallback"
              style="width: 200px;"
            >
              <el-option
                v-for="item in areaInfo.cityList"
                :key="item.id"
                :label="item.name"
                :value="Number(item.id)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="districtId">
            <el-select
              v-model.number="areaInfo.districtId"
              placeholder="请选择区域"
              clearable
              @change="districtChangeCallback"
              @clear="districtClearCallback"
              style="width: 200px;"
            >
              <el-option
                v-for="item in areaInfo.districtList"
                :key="item.id"
                :label="item.name"
                :value="Number(item.id)"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="apartmentId">
            <el-select
              v-model.number="areaInfo.apartmentId"
              placeholder="请选择公寓"
              clearable
              @change="apartmentChangeCallback"
              @clear="apartmentClearCallback"
              style="width: 200px;"
            >
              <el-option
                v-for="item in areaInfo.apartmentList"
                :key="item.id"
                :label="item.name"
                :value="Number(item.id)"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
      </el-form-item>
      <el-form-item label="是否发布" prop="isRelease">
        <el-radio-group v-model="formData.isRelease" class="ml-4">
          <el-radio :label="RoomReleaseStatus.NOT_RELEASED">
            {{
              getLabelByValue(
                RoomReleaseStatusMap,
                RoomReleaseStatus.NOT_RELEASED,
              )
            }}
          </el-radio>
          <el-radio :label="RoomReleaseStatus.RELEASED">
            {{
              getLabelByValue(RoomReleaseStatusMap, RoomReleaseStatus.RELEASED)
            }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="房间属性" prop="attrValueIds">
        <el-tree-select
          style="width: 100%"
          v-model="formData.attrValueIds"
          placeholder="请选择房间属性"
          :data="attrInfoList"
          multiple
          clearable
          node-key="value"
          :render-after-expand="false"
          @node-click="attrNodeClickHandle"
        ></el-tree-select>
      </el-form-item>
      <el-form-item label="房间配套" prop="facilityInfoIds">
        <el-select
          style="width: 100%"
          v-model="formData.facilityInfoIds"
          placeholder="请选择房间配套"
          multiple
          clearable
        >
          <el-option
            v-for="item in facilityInfoList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="房间标签" prop="labelIds">
        <el-select
          style="width: 100%"
          v-model="formData.labelInfoIds"
          placeholder="请选择房间标签"
          multiple
          clearable
        >
          <el-option
            v-for="item in labelInfoList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="支付方式" prop="paymentTypeIds">
        <el-select
          style="width: 100%"
          v-model="formData.paymentTypeIds"
          placeholder="请选择支付方式"
          multiple
          clearable
        >
          <el-option
            v-for="item in paymentInfoList"
            :key="item.id"
            :label="`${item.name} (${item.additionalInfo})`"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="可选租期" prop="leaseTermIds">
        <el-select
          style="width: 100%"
          v-model="formData.leaseTermIds"
          placeholder="请选择可选租期"
          multiple
          clearable
        >
          <el-option
            v-for="item in leaseTermInfoList"
            :key="item.id"
            :label="`${item.monthCount}${item.unit}`"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图片" prop="graphVoList">
        <upload-img
          v-model:file-list="formData.graphVoList"
          :on-success="uploadSuccessHandle"
          list-type="picture-card"
          :limit="5"
        ></upload-img>
      </el-form-item>
    </el-form>
    <div class="flex-center m-t-20">
      <el-button style="width: 150px" type="info" @click="router.back()">
        取消
      </el-button>
      <el-button style="width: 150px" type="primary" @click="submitHandle">
        {{ formData.id ? '保存' : '新增' }}
      </el-button>
    </div>
  </el-card>
</template>
<script setup lang="ts">
import { nextTick, onMounted, reactive, ref, watch } from 'vue'
import { ElMessage, FormInstance, UploadFiles } from 'element-plus'
import {
  ApartmentInterface,
  FacilityInfoInterface,
  LabelInfoInterface,
  PaymentInfoInterface,
  RegionInterface,
  SaveRoomInterface,
  TermInfoInterface,
  TreeData,
} from '@/api/apartmentManagement/types'
import {
  getApartmentListByDistrictId,
  getAttrInfoList,
  getCityList,
  getDistrictList,
  getFacilityInfoList,
  getLabelInfoList,
  getPaymentList,
  getProvinceList,
  getRoomById,
  getTermList,
  saveOrUpdateRoom,
} from '@/api/apartmentManagement'
import { UploadFile } from 'element-plus/es/components/upload/src/upload'
import UploadImg from '@/components/uploadImg/uploadImg.vue'
import { useRoute, useRouter } from 'vue-router'
// import { ElTree } from 'element-plus/es/components/tree'
import {
  BuildingType,
  getLabelByValue,
  RoomReleaseStatus,
  RoomReleaseStatusMap,
} from '@/enums/constEnums'
const route = useRoute()
const router = useRouter()
//#region <表单相关>
// 表单数据
const apartmentFormRef = ref<FormInstance>()
const formData = ref<SaveRoomInterface>({
  id: '',
  roomNumber: '',
  rent: 0,
  apartmentId: '',
  isRelease: RoomReleaseStatus.NOT_RELEASED,
  // 	属性信息列表
  attrValueIds: [],
  // 	配套id
  facilityInfoIds: [],
  // 	标签信息列表
  labelInfoIds: [],
  // 	支付方式列表
  paymentTypeIds: [],
  // 可选租期列表
  leaseTermIds: [],
  graphVoList: [
    // {
    //   name: 'food.jpeg',
    //   url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
    // },
  ] as UploadFile[],
})
// 表单验证规则
const rules = reactive({
  roomNumber: [{ required: true, message: '请输入房间号', trigger: 'blur' }],
  rent: [{ required: true, message: '请输入租金', trigger: 'blur' }],
  apartmentId: [{ required: true, message: '请选择公寓', trigger: 'change' }],
  attrValueIds: [{ required: true, message: '请选择属性', trigger: 'change' }],
  facilityInfoIds: [
    { required: true, message: '请选择配套', trigger: 'change' },
  ],
  labelInfoIds: [{ required: true, message: '请选择标签', trigger: 'change' }],
  paymentTypeIds: [
    { required: true, message: '请选择支付方式', trigger: 'change' },
  ],
  leaseTermIds: [
    { required: true, message: '请选择可选租期', trigger: 'change' },
  ],
  graphVoList: [{ required: true, message: '请上传图片', trigger: 'change' }],
})
//#endregion
//#region <省市区查询相关>
// 地区数据
const areaInfo = reactive({
  // 省份数据
  provinceList: [] as RegionInterface[],
  provinceId: null as number | null,
  // 市区数据
  cityList: [] as RegionInterface[],
  cityId: null as number | null,
  // 区县数据
  districtList: [] as RegionInterface[],
  districtId: null as number | null,
  // 公寓数据
  apartmentList: [] as ApartmentInterface[],
  apartmentId: null as number | null,
})
// 监视地区信息变化,更新公寓信息
watch(
  () => areaInfo,
  (newVal) => {
    formData.value.apartmentId = newVal.apartmentId != null ? String(newVal.apartmentId) : ''
  },
  { immediate: true, deep: true },
)
// 获取省份
async function getProvinceListHandle() {
  try {
    const { data } = await getProvinceList()
    areaInfo.provinceList = data
  } catch (error) {
    console.log(error)
  }
}
// 获取城市
async function getCityListHandle(
  provinceId: number | string | null = areaInfo.provinceId,
) {
  try {
    const { data } = await getCityList(provinceId)
    areaInfo.cityList = data
  } catch (error) {
    console.log(error)
  }
}
// 获取区域
async function getDistrictListHandle(
  cityId: number | string | null = areaInfo.cityId,
) {
  try {
    const { data } = await getDistrictList(cityId)
    areaInfo.districtList = data
  } catch (error) {
    console.log(error)
  }
}
// 获取公寓
async function getApartmentListHandle(
  districtId: number | string | null = areaInfo.districtId,
) {
  try {
    const { data } = await getApartmentListByDistrictId(districtId)
    areaInfo.apartmentList = data
  } catch (error) {
    console.log(error)
  }
}
// 重置市数据
function resetCity() {
  areaInfo.cityId = null
  areaInfo.cityList = []
}
// 重置区数据
function resetDistrict() {
  areaInfo.districtId = null
  areaInfo.districtList = []
}
// 重置公寓数据
function resetApartment() {
  areaInfo.apartmentId = null
  areaInfo.apartmentList = []
}
// 省份改变回调
const provinceChangeCallback = async () => {
  console.log(1)
  let provinceId = areaInfo.provinceId
  if (provinceId) {
    resetCity()
    resetDistrict()
    resetApartment()
    await getCityListHandle(provinceId)
  }
}
// 省份清除回调
const provinceClearCallback = () => {
  areaInfo.provinceId = null
  resetCity()
  resetDistrict()
  resetApartment()
}
// 城市改变回调
const cityChangeCallback = async () => {
  let cityId = areaInfo.cityId
  if (cityId) {
    resetDistrict()
    resetApartment()
    await getDistrictListHandle(cityId)
  }
}
// 城市清除回调
const cityClearCallback = () => {
  console.log('清空城市')
  areaInfo.cityId = null
  resetDistrict()
  resetApartment()
}
// 区域改变回调
const districtChangeCallback = async () => {
  let districtId = areaInfo.districtId
  if (districtId) {
    resetApartment()
    await getApartmentListHandle(districtId)
  }
}
// 区域清除回调
const districtClearCallback = () => {
  console.log('清空区域')
  areaInfo.districtId = null
  resetApartment()
}
// 公寓改变回调
const apartmentChangeCallback = async () => {
  console.log('公寓改变')
}
// 公寓清除回调
const apartmentClearCallback = () => {
  console.log('清空公寓')
  areaInfo.apartmentId = null
}
//#endregion
//#region <配套信息、标签信息、属性信息>
// 配套信息
const facilityInfoList = ref<FacilityInfoInterface[]>([])
// 标签信息
const labelInfoList = ref<LabelInfoInterface[]>([])
// 属性信息
const attrInfoList = ref<TreeData[]>([])
// 支付方式信息
const paymentInfoList = ref<PaymentInfoInterface[]>([])
// 可选租期信息
const leaseTermInfoList = ref<TermInfoInterface[]>([])
// 配套信息树实例
// const attrTreeSelectRef = ref<InstanceType<typeof ElTree>>()
// 获取配套信息
async function getFacilityInfoListHandle() {
  try {
    const { data } = await getFacilityInfoList(BuildingType.ROOM)
    facilityInfoList.value = data
  } catch (error) {
    console.log(error)
  }
}
// 获取标签信息
async function getLabelInfoListHandle() {
  try {
    const { data } = await getLabelInfoList(BuildingType.ROOM)
    labelInfoList.value = data
  } catch (error) {
    console.log(error)
  }
}
// 获取属性信息
async function getAttrInfoListHandle() {
  try {
    const { data } = await getAttrInfoList()
    attrInfoList.value = data.map((item) => {
      return {
        value: item.id + item.name,
        label: item.name,
        children: item.attrValueList.map((child) => {
          return {
            value: child.id,
            label: `${child.name}(${item.name})`,
            parentId: item.id + item.name,
          }
        }),
      }
    })
  } catch (error) {
    console.log(error)
  }
}
// 获取支付方式信息列表
async function getPaymentInfoListHandle() {
  try {
    const { data } = await getPaymentList()
    paymentInfoList.value = data
  } catch (error) {
    console.log(error)
  }
}
// 获取可选租期信息列表
async function getLeaseTermInfoListHandle() {
  try {
    const { data } = await getTermList()
    leaseTermInfoList.value = data
  } catch (error) {
    console.log(error)
  }
}

// 属性信息节点点击回调
function attrNodeClickHandle(data: TreeData) {
  //   保证每个子节点只有一个被选中
  if (data.parentId) {
    //   收集当前父节点下所有的子节点列表
    const childrenList = attrInfoList.value
      .find((item) => item.value === data.parentId)
      ?.children?.map((item) => item.value)
  }
}
//#endregion
// 图片上传成功
function uploadSuccessHandle(
  response: any,
  uploadFile: UploadFile,
  uploadFiles: UploadFiles,
) {
  formData.value.graphVoList = uploadFiles?.map((item) => {
    return {
      ...item,
      url: (item?.response as any)?.data || item.url,
    }
  })
}
// 根据id获取房间信息
async function getRoomInfoByIdHandle(id: number | string) {
  try {
    const { data } = await getRoomById(id)
    formData.value = data as unknown as SaveRoomInterface
    // 	属性ids
    formData.value.attrValueIds =
      data.attrValueVoList?.map((item) => item.id) || []
    delete data.attrValueVoList
    // 	配套ids
    formData.value.facilityInfoIds =
      (data.facilityInfoList?.map((item) => item.id) as number[]) || []
    delete data.facilityInfoList
    // 	标签ids
    formData.value.labelInfoIds =
      (data.labelInfoList?.map((item) => item.id) as number[]) || []
    delete data.labelInfoList
    // 	支付方式ids
    formData.value.paymentTypeIds =
      (data.paymentTypeList?.map((item) => item.id) as number[]) || []
    delete data.paymentTypeList
    // 可选租期ids
    formData.value.leaseTermIds =
      (data.leaseTermList?.map((item) => item.id) as number[]) || []
    delete data.leaseTermList
    // 重置省市区
    // 获取城市
    areaInfo.provinceId = Number(data.apartmentInfo.provinceId)
    areaInfo.cityId = Number(data.apartmentInfo.cityId)
    areaInfo.districtId = Number(data.apartmentInfo.districtId)
    areaInfo.apartmentId = Number(data.apartmentId)
    data.apartmentInfo.provinceId &&
      getCityListHandle(data.apartmentInfo.provinceId)
    // 获取区域
    data.apartmentInfo.cityId &&
      getDistrictListHandle(data.apartmentInfo.cityId)
    // 获取公寓
    data.apartmentInfo.districtId &&
      getApartmentListHandle(data.apartmentInfo.districtId)
  } catch (error) {
    console.log(error)
  }
}
// 新增或更新公寓信息
async function addOrUpdateRoomInfoHandle() {
  try {
    await saveOrUpdateRoom(formData.value)
    ElMessage.success('操作成功')
    router.back()
  } catch (error) {
    console.log(error)
  }
}
// 提交
function submitHandle() {
  apartmentFormRef.value?.validate(async (valid) => {
    if (valid) {
      await addOrUpdateRoomInfoHandle()
    } else {
      ElMessage.error('表单填写有误，请检查')
      return false
    }
  })
}
onMounted(() => {
  // 获取省份
  getProvinceListHandle()
  // 获取配套信息
  getFacilityInfoListHandle()
  // 获取标签信息
  getLabelInfoListHandle()
  // 获取属性信息
  getAttrInfoListHandle()
  // 获取支付方式信息列表
  getPaymentInfoListHandle()
  // 获取可选租期信息列表
  getLeaseTermInfoListHandle()
  if (route.query?.id) {
    getRoomInfoByIdHandle(route.query.id as string)
  }
})
// 监听省份ID变化，看是否能实时捕获
watch(() => areaInfo.provinceId, (newVal, oldVal) => {
  console.log('省份ID旧值：', oldVal);
  console.log('省份ID新值：', newVal);
  // 同时打印对应省份名称，确认能找到匹配项
  const targetProvince = areaInfo.provinceList.find(item => item.id === newVal);
  console.log('匹配到的省份：', targetProvince); // 若能打印出 {id:11, name:'北京市'}，说明响应式正常
}, { immediate: false });
</script>

<style scoped lang="scss">
.card-header {
  font-size: 18px;
  font-weight: bold;
}

// 强制修复 el-select 文字不显示问题（省份下拉框专属）
:deep(.el-select) {
  // 针对下拉框输入框的文字样式
  .el-input__inner {
    color: #333 !important; /* 强制设置文字颜色为深灰色，确保与白色背景对比明显 */
    opacity: 1 !important; /* 强制不透明，取消任何透明设置 */
    font-size: 14px !important; /* 强制设置文字大小，避免字体为0 */
    background-color: #ffffff !important; /* 强制背景为白色，与文字形成对比 */
    border: 1px solid #dcdfe6 !important; /* 可选：确保输入框边框可见，确认组件位置 */
    padding-left: 15px !important; /* 可选：避免文字左对齐超出可视区域 */
  }

  // 针对选中后显示的标签文字样式
  .el-select__selected-label {
    color: #333 !important; /* 强制选中标签文字颜色可见 */
    opacity: 1 !important;
    font-size: 14px !important;
  }

  // 针对下拉列表中的选项文字样式（顺带修复，避免选项也看不见）
  .el-select-dropdown {
    .el-select-dropdown__item {
      color: #333 !important;
      font-size: 14px !important;
      opacity: 1 !important;
    }
    // 选中的下拉选项文字高亮
    .el-select-dropdown__item.selected {
      color: #409eff !important; /* Element Plus 主题色，确保选中项醒目 */
      background-color: #ecf5ff !important;
    }
  }
}
</style>
