<template>

  <div class="table_container">
    <div class="fillcontain">

      <el-row :gutter="20">
        <el-col :span="5">
          <el-input v-model="listQuery.name" placeholder="食品名称"></el-input>
        </el-col>
        <el-col :span="3">
          <el-input v-model="listQuery.name" placeholder="商家id"></el-input>
        </el-col>
        <el-col :span="3">
          <el-input v-model="listQuery.name" placeholder="商家外卖分类id"></el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="success" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>
      <br>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="食品名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="餐馆名称">
                <span>{{ props.row.shopName }}</span>
              </el-form-item>
              <el-form-item label="食品 ID">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="餐馆 ID">
                <span>{{ props.row.shopId }}</span>
              </el-form-item>
              <el-form-item label="食品介绍">
                <span>{{ props.row.description }}</span>
              </el-form-item>
              <el-form-item label="餐馆地址">
                <span>{{ props.row.shopAddress }}</span>
              </el-form-item>
              <el-form-item label="食品评分">
                <span>{{ props.row.rating }}</span>
              </el-form-item>
              <el-form-item label="食品分类">
                <span>{{ props.row.categoryName }}</span>
              </el-form-item>
              <el-form-item label="月销量">
                <span>{{ props.row.monthSales }}</span>
              </el-form-item>
              <el-form-item label="是否为规格商品">
                <span>{{ props.row.single == true ? "否" : "是" }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="食品名称" prop="name">
        </el-table-column>
        <el-table-column label="食品介绍" prop="description">
        </el-table-column>
        <el-table-column label="评分" prop="rating">
        </el-table-column>
        <el-table-column label="状态" prop="stateStr">
        </el-table-column>
        <el-table-column label="操作" width="450">
          <template slot-scope="scope">
            <el-button v-if="scope.row.isSingle" size="small" type="info" @click="handleSingleEdit(scope.row)">编辑非规
            </el-button>
            <el-button v-else size="small" type="info" @click="handleEdit(scope.row)">编辑规</el-button>
            <el-button v-if="!scope.row.isSingle" size="small" type="info" @click="handAddspecsCategory(scope.row)">
              添加规格分类</el-button>
            <el-button v-if="!scope.row.isSingle" size="small" type="info" @click="handAddspecs(scope.row)">添加规格
            </el-button>
            <el-button v-if="scope.row.stateStr !== '已删除'" size="small" type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100, 500]"
        :page-size="listQuery.limit" :total="total" @size-change="changeSize" @current-change="fetchPage"
        @prev-click="fetchPrev" @next-click="fetchNext">
      </el-pagination>
      <!-- ---------修改非规格食品信息---------- -->
      <el-dialog title="修改非规格食品信息" :visible.sync="dialogFormVisibleSingle">
        <el-form :model="selectTable">
          <el-form-item label="食品名称" label-width="100px">
            <el-input v-model="selectTable.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="食品价格" label-width="100px">
            <el-input v-model="selectTable.price" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="打包费" label-width="100px">
            <el-input v-model="selectTable.packingFee" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="食品介绍" label-width="100px">
            <el-input v-model="selectTable.description"></el-input>
          </el-form-item>
          <el-form-item label="食品分类" label-width="100px">
            <el-select v-model="selectIndex" :placeholder="selectMenu.label" @change="handleSelect">
              <el-option v-for="item in menuOptions" :key="item.value" :label="item.label" :value="item.index">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="食品图片" label-width="100px">
            <el-upload class="avatar-uploader" :show-file-list="false"
              action="http://127.0.0.1:8080/boot/customer/image" :before-upload="handleBeforeUpload"
              :on-success="handleUploadSuccess">
              <img v-if="selectTable.imagePath" :src="selectTable.imagePath" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibleSingle = false">取 消</el-button>
          <el-button type="primary" @click="updateSingleFood">确 定</el-button>
        </div>
      </el-dialog>

      <!-- ---------修改规格食品信息---------- -->
      <el-dialog title="修改规格食品信息" :visible.sync="dialogFormVisible">
        <el-form :model="selectTable">
          <el-form-item label="食品名称" label-width="100px">
            <el-input v-model="selectTable.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="食品介绍" label-width="100px">
            <el-input v-model="selectTable.description"></el-input>
          </el-form-item>
          <el-form-item label="食品分类" label-width="100px">
            <el-select v-model="selectIndex" :placeholder="selectMenu.label" @change="handleSelect">
              <el-option v-for="item in menuOptions" :key="item.value" :label="item.label" :value="item.index">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="食品图片" label-width="100px">
            <el-upload class="avatar-uploader" :show-file-list="false"
              action="http://127.0.0.1:8080/boot/customer/image" :before-upload="handleBeforeUpload"
              :on-success="handleUploadSuccess">
              <img v-if="selectTable.imagePath" :src="selectTable.imagePath" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-row style="overflow: auto; text-align: center;">
          <el-table :data="specs" style="margin-bottom: 20px;" :row-class-name="tableRowClassName">
            <el-table-column prop="specsList" label="规格">
            </el-table-column>
            <el-table-column prop="packingFee" label="包装费">
            </el-table-column>
            <el-table-column prop="price" label="价格">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="small" type="danger" @click="deleteSpecs(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" @click="AddSkuDetailVisible = true" style="margin-bottom: 10px;">添加规格</el-button>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateFood">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 添加规格商品sku -->
      <el-dialog title="添加规格商品sku" :visible.sync="AddSkuDetailVisible">
        <el-form :model="skuForm">
          <el-form-item label="规格" label-width="100px">
            <el-cascader expand-trigger="hover" :options="specsTreeOption" v-model="selectedSpecsArr"
              @change="handleSelectSpecsChange">
            </el-cascader>
          </el-form-item>
          <el-form-item label="规格清单" label-width="100px">
            <el-tag v-for="tag in specsTags" :key="tag.name" :type="tag.type">
              {{ tag.name }}
            </el-tag>

          </el-form-item>
          <el-form-item label="规格列表" label-width="100px">
            <el-input v-model="skuForm.specsList" auto-complete="off" disabled></el-input>
          </el-form-item>
          <el-form-item label="价格" label-width="100px">
            <el-input v-model="skuForm.price" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="打包费" label-width="100px">
            <el-input v-model="skuForm.packingFee" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="AddSkuDetailVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSkuFood">确 定</el-button>
        </div>
      </el-dialog>
      <!-- ---------添加规格---------- -->

      <el-dialog title="添加规格" :visible.sync="AddspecsDetailVisible">
        <el-form :model="addSpecsForm">
          <el-form-item label="选择规格分类" label-width="100px">
            <el-select v-model="selectSpecsCategoryIndex" :placeholder="selectSpecsCategoryMenu.label"
              @change="handleSpecsCategorySelect">
              <el-option v-for="item in specsCategoryOptions" :key="item.value" :label="item.label" :value="item.index">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="规格名称" label-width="100px">
            <el-input v-model="addSpecsForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="规格描述" label-width="100px">
            <el-input v-model="addSpecsForm.description" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="AddspecsDetailVisible = false">取 消</el-button>
          <el-button type="primary" @click="addspecs">确 定</el-button>
        </div>
      </el-dialog>
      <!-- ---------添加规格分类---------- -->
      <el-dialog title="添加规格分类" :visible.sync="AddspecsCategoryVisible">
        <el-form :model="specsForm">
          <el-form-item label="名称" label-width="100px">
            <el-input v-model="addSpecsCategoryForm.name" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="AddspecsCategoryVisible = false">取 消</el-button>
          <el-button type="primary" @click="addspecsCategory">确 定</el-button>
        </div>
      </el-dialog>
      <!-- sku列表 对sku进行删除操作 -->
      <el-dialog title="规格商品列表" :visible.sync="SkuListVisible">
        <el-form :model="SkuList">
          <el-form-item label="名称" label-width="100px">
            <el-table>
              <el-table-column prop="specsList" label="规格">
              </el-table-column>
              <el-table-column prop="packingFee" label="包装费">
              </el-table-column>
              <el-table-column prop="price" label="价格">
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button size="small" type="danger" @click="deleteSku(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="AddspecsCategoryVisible = false">取 消</el-button>
          <el-button type="primary" @click="addspecsCategory">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script src="./food.js"></script>

<style lang="less">
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.table_container {
  padding: 20px;
}

.Pagination {
  display: flex;
  justify-content: flex-start;
  margin-top: 8px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>
