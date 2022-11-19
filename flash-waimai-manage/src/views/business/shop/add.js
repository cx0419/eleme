import { getApiUrl } from '@/utils/utils'
import { getToken } from '@/utils/auth'
import { cityGuess, searchplace } from '@/api/getData'
import { addShop, foodCategory,getShopClass } from '@/api/business/shop'

export default {
  data() {
    return {
      city: {},
      fileMgrUrl: 'http://127.0.0.1:8080/boot/customer/image',
      uploadHeaders: {
        'Authorization': ''
      },
      formData: {
        password:'',
        name: '', //店铺名称
        address: '', //地址
        latitude: '',
        longitude: '',
        description: '', //介绍
        phone: '',
        promotionInfo: '',
        floatDeliveryFee: 5, //运费
        floatMinimumOrderAmount: 20, //起价
        isPremium: true,
        deliveryMode: true,
        news: true,
        bao: true,
        zhun: true,
        piao: true,
        startTime: '',
        endTime: '',
        imagePath: '',
        businessLicenseImage: '',
        cateringServiceLicenseImage: '',
        platformRate:'10',
        shopClassId:0,

      },
      rules: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入店铺名称', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入详细地址', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话' },
          { type: 'number', message: '电话号码必须是数字' }
        ]
      },
      options: [{
        value: '满减优惠',
        label: '满减优惠'
      }, {
        value: '优惠大酬宾',
        label: '优惠大酬宾'
      }, {
        value: '新用户立减',
        label: '新用户立减'
      }, {
        value: '进店领券',
        label: '进店领券'
      }],
      activityValue: '满减优惠',
      activities: [{
        icon_name: '减',
        name: '满减优惠',
        description: '满30减5，满60减8'
      }],
      categoryOptions: [],
      selectedCategory: [],
    }
  },
  mounted() {
    this.initData()
    this.uploadHeaders['Authorization'] = getToken()
  },
  methods: {
    async initData() {
        //初始化商店类型选择项
        const resp = await getShopClass();
        this.formData.shopClassId= resp.data.data[0].id;
        this.selectedCategory[0] = resp.data.data[0].id;
        this.categoryOptions = resp.data.data.map((item) => {
          return {
              "label": item['name'],
              "value": item['id'],
          }
        })
    },
    async querySearchAsync(queryString, cb) {
      if (queryString) {
        try {
          const cityListResponse = await searchplace(this.city.id, queryString)
          const cityList = cityListResponse.data
          if (cityList instanceof Array) {
            cityList.map(item => {
              item.value = item.address
              return item
            })
            cb(cityList)
          }
        } catch (err) {
          console.log(err)
        }
      }
    },
    
    addressSelect(address) {
      this.formData.latitude = address.latitude
      this.formData.longitude = address.longitude
    },
    //上传头像的回调函数
    handleShopAvatarScucess(res, file) {
      console.log(res)
      this.formData.imagePath = res.data
      // this.formData.imagePath = res.data.data;
    },
    handleBusinessAvatarScucess(res, file) {
      this.formData.businessLicenseImage = res.data
      // this.formData.businessLicenseImage = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName
    },
    handleServiceAvatarScucess(res, file) {
      this.formData.cateringServiceLicenseImage = res.data
      // this.formData.cateringServiceLicenseImage = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName
    },
    beforeAvatarUpload(file) {
      const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png')
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isRightType) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isRightType && isLt2M
    },
    tableRowClassName(row, index) {
      if (index === 1) {
        return 'info-row'
      } else if (index === 3) {
        return 'positive-row'
      }
      return ''
    },
    selectActivity() {
      this.$prompt('请输入活动详情', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        if (value === null) {
          this.$message({
            type: 'info',
            message: '请输入活动详情'
          })
          return
        }
        let newObj = {}
        switch (this.activityValue) {
          case '满减优惠':
            newObj = {
              icon_name: '减',
              name: '满减优惠',
              description: value
            }
            break
          case '优惠大酬宾':
            newObj = {
              icon_name: '特',
              name: '优惠大酬宾',
              description: value
            }
            break
          case '新用户立减':
            newObj = {
              icon_name: '新',
              name: '新用户立减',
              description: value
            }
            break
          case '进店领券':
            newObj = {
              icon_name: '领',
              name: '进店领券',
              description: value
            }
            break
        }
        this.activities.push(newObj)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    handleDelete(index) {
      this.activities.splice(index, 1)
    },
    gotologin(){
      this.$router.push('/login')
    },
    submitForm(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          Object.assign(this.formData, { activities: this.activities }, {
            category: this.selectedCategory.join('/')
          })
          const activities = this.formData.activities
          const activitiesJson = JSON.stringify(activities)
          delete this.formData.activities
          this.formData.activitiesJson = activitiesJson
          this.formData.shopClassId = this.selectedCategory[0];
          addShop(this.formData).then(response => {
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.$message({
              type: 'success',
              message: '您的账号为:'+ response.data.data+",请等待管理员通过您的商家申请!"
            })
            this.$router.push(-1)
            this.formData = {
              password:'',
              name: '', //店铺名称
              address: '', //地址
              latitude: '',
              longitude: '',
              description: '', //介绍
              phone: '',
              promotionInfo: '',
              floatDeliveryFee: 5, //运费
              floatMinimumOrderAmount: 20, //起价
              isPremium: true,
              deliveryMode: true,
              news: true,
              bao: true,
              zhun: true,
              piao: true,
              startTime: '',
              endTime: '',
              imagePath: '',
              businessLicenseImage: '',
              cateringServiceLicenseImage: '',
              shopClassId:0
            }
            this.selectedCategory = []
            this.activities = [{
              icon_name: '减',
              name: '满减优惠',
              description: '满30减5，满60减8'
            }]
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: '请检查输入是否正确',
            offset: 100
          })
          return false
        }
      })
    }
  }
}
