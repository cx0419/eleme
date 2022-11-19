// 存储localStorage

const LocalStorage = {
	setLocal : (key, value) => {
		localStorage.setItem(key, JSON.stringify(value));
	},
	getLocal : (key) => {
		return JSON.parse(localStorage.getItem(key))
	},
	delLocal : (key) => {
		localStorage.removeItem(key);
	},
	clearLocal : () => {
		localStorage.clear();
	}
}

 export default LocalStorage;