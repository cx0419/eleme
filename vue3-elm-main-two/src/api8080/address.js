import axios02 from "../utils/axios02";

export function addNewAddress(userId, address, address_detail, geohash, name, phone, phone_bk, poi_type, sex, tag, tag_type) {
    return axios02.post('/v1/users/' + userId + '/addresses', {
        address,
        address_detail,
        geohash,
        name,
        phone,
        phone_bk,
        poi_type,
        sex,
        tag,
        tag_type
    })
}

// 删除地址
export function deleteAddress(userid, addressid) {
    return axios02.delete('/v1/users/' + userid + '/addresses/' + addressid, {})
}

//根据用户id获取收获地址列表
export function getAddressListByCId(cid){
    return axios02.get("/address/"+cid)
}