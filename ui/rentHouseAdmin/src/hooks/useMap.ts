// 高德地图hooks
import AMapLoader from '@amap/amap-jsapi-loader'
import {AMAP_MAP_KEY, AMAP_MAP_SECRET_KEY} from '@/config/config'
import { ElMessage } from 'element-plus'
import { onMounted, onUnmounted, ref } from 'vue'

export const useMap = ({ domId }: { domId?: string } = {}) => {
  const AMap = ref()
  const map = ref()
  async function initMap() {
    try {
      ;(window as any)._AMapSecurityConfig = {
        securityJsCode: AMAP_MAP_SECRET_KEY,
        // serviceHost: AMAP_MAP_SERVICE_HOST,
      }
      AMap.value = await AMapLoader.load({
        key: AMAP_MAP_KEY, // 申请好的Web端开发者Key，首次调用 load 时必填
        version: '2.0', // 指定要加载的 JS API 的版本，缺省时默认为 1.4.15
        plugins: [], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      })
      map.value = domId && new AMap.value.Map(domId)
    } catch (error) {
      console.log(error)
      //ElMessage.error((error as any)?.message || '')
    }
  }
  function destroyMap() {
    map.value && map.value.destroy()
  }
  // onMounted(() => {
  //
  //   initMap()
  // })
  onMounted(async () => {
    await initMap(); // 等待地图初始化完成
  })
  onUnmounted(() => {
    destroyMap()
  })
  return {
    map,
    AMap,
  }
}
// 高德地图hooks
// import AMapLoader from '@amap/amap-jsapi-loader'
// import { AMAP_MAP_KEY, AMAP_MAP_SECRET_KEY } from '@/config/config'
// import { ElMessage } from 'element-plus'
// import { onMounted, onUnmounted, ref } from 'vue'
//
// export const useMap = ({ domId }: { domId?: string } = {}) => {
//   // 修正：明确初始值为 null，类型更清晰
//   const AMap = ref<null | any>(null)
//   const map = ref<null | any>(null)
//
//   async function initMap() {
//     try {
//       ;(window as any)._AMapSecurityConfig = {
//         securityJsCode: AMAP_MAP_SECRET_KEY,
//         // serviceHost: AMAP_MAP_SERVICE_HOST,
//       }
//       // 修正2：提前加载 AMap.AutoComplete 插件，满足地址搜索需求
//       AMap.value = await AMapLoader.load({
//         key: AMAP_MAP_KEY, // 申请好的Web端开发者Key，首次调用 load 时必填
//         version: '2.0', // 指定要加载的 JS API 的版本，缺省时默认为 1.4.15
//         plugins: ['AMap.AutoComplete'], // 提前加载地址自动补全插件
//       })
//       // 仅当传递 domId 时，才实例化地图可视化实例
//       if (domId && AMap.value) {
//         map.value = new AMap.value.Map(domId)
//       }
//     } catch (error) {
//       console.error('高德地图初始化失败：', error)
//       ElMessage.error((error as any)?.message || '高德地图加载失败，请稍后重试')
//       // 初始化失败时，重置为 null
//       AMap.value = null
//       map.value = null
//     }
//   }
//
//   function destroyMap() {
//     // 销毁地图可视化实例
//     if (map.value) {
//       map.value.destroy()
//       map.value = null // 重置 map 实例
//     }
//     // 修正：重置 AMap 实例，避免内存泄漏
//     AMap.value = null
//   }
//
//   onMounted(async () => {
//     // 修正1：异步函数添加 await，等待地图加载完成
//     await initMap()
//   })
//
//   onUnmounted(() => {
//     destroyMap()
//   })
//
//   return {
//     map,
//     AMap,
//   }
// }