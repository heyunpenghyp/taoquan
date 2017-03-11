//是否为空
String.prototype.isNull = function(){
	if (this == null || this.trim()== "") {
		return true;
	} else {
		return false;
	}
}

//isNull ->""
String.prototype.trimNull = function(){
	if (this.isNull()) {
		return "";
	} else {
		return this.trim();
	}
}

//左TRIM
String.prototype.ltrim = function() {
	return this.replace(/^\s*/,"");
}

//右TRIM
String.prototype.rtrim = function() {
	return this.replace(/\s*$/,"");
}

// TRIM函数
String.prototype.trim  = function() {
	return this.ltrim().rtrim();
}

String.prototype.endWith = function(str){
	var reg = new RegExp(str + "$");
	return reg.test(this);
}

// 判断是否属于整数型
String.prototype.isInteger = function() {
	if (this.trim().search(/^[-]?[0-9]*$/)==-1) {
		return false;
	} else {
		return true;
	}
}

//新密码判断
String.prototype.isPassword=function(){
	if (this.trim().search( /^[a-zA-Z0-9]{8,30}$/) == -1) {
		return false;
	} else {
		return true;
	}
}

// 判断是否属于正整数型
String.prototype.isPositiveInteger = function() {
	if (this.trim().search(/^[0-9]*[1-9][0-9]*$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断是否属于自然数
String.prototype.isNatural = function(){
	if (this.trim().search(/^((0)|([1-9][0-9]*))$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断是否属于自然数
String.prototype.isEnglish = function(){
    if (this.trim().search(/^([a-zA-Z]*)$/)==-1) {
        return false;
    } else {
        return true;
    }
}


// 判断是否属于浮点型
String.prototype.isFloat = function() {
	if (this.trim().search(/^[-]?[0-9]*[.]?[0-9]*$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断是否属于价格
String.prototype.isPrice = function() {
		if (this.trim().search(/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/)==-1) {
			return false;
		} else {
			return true;
		}
}


// 判断是否15位身份证号
String.prototype.isIDCard15 = function(){
	if (this.trim().search(/^(\d{15})$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断身份证号码(15或者18位)
String.prototype.isIDCard = function() {
	if (this.trim().search(/^((\d{15})|(\d{17}[0-9xX]))$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断18位身份证号码
String.prototype.isIDCard18 = function(){
	if (this.trim().search(/^(\d{17}[0-9xX])$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断台湾身份证号码
String.prototype.isIDCard10 = function() {
	if (this.length < 10) {
		return false;
	}

	var total = 0;
	for (var i = 0; i < 9; i++) {
		if (i == 0) {	
			var temp;
			var chr = this.charAt(i);
			if (chr == 'A') {
				temp = 10;
			} else if (chr == 'B') {
				temp = 11;
			} else if (chr == 'C') {
				temp = 12;
			} else if (chr == 'D') {
				temp = 13;
			} else if (chr == 'E') {
				temp = 14;
			} else if (chr == 'F') {
				temp = 15;
			} else if (chr == 'G') {
				temp = 16;
			} else if (chr == 'H') {
				temp = 17;
			} else if (chr == 'I') {
				temp = 34;
			} else if (chr == 'J') {
				temp = 18;
			} else if (chr == 'K') {
				temp = 19;
			} else if (chr == 'L') {
				temp = 20;
			} else if (chr == 'M') {
				temp = 21;
			} else if (chr == 'N') {
				temp = 22;
			} else if (chr == 'O') {
				temp = 35;
			} else if (chr == 'P') {
				temp = 23;
			} else if (chr == 'Q') {
				temp = 24;
			} else if (chr == 'R') {
				temp = 25;
			} else if (chr == 'S') {
				temp = 26;
			} else if (chr == 'T') {
				temp = 27;
			} else if (chr == 'U') {
				temp = 28;
			} else if (chr == 'V') {
				temp = 29;
			} else if (chr == 'W') {
				temp = 32;
			} else if (chr == 'X') {
				temp = 30;
			} else if (chr == 'Y') {
				temp = 31;
			} else if (chr == 'Z') {
				temp = 33;
			} else {
				return false;
			}
			total += parseInt((temp + "").substring(0, 1));
			total += (parseInt((temp + "").substring(1))) * 9;
		} else {
			total += (9 - i) * parseInt(this.charAt(i));
		}
	}
	if (total % 10 == 0) {
		return 0 == parseInt(this.charAt(9));
	}
	return (10 - total % 10) == parseInt(this.charAt(9));
}

// 判断手机号码
String.prototype.isMobilePhone = function(){
	if (this.trim().search(/^[0-9 | -]*$/)==-1) {///^((13|15|18)\d{9})$/
		return false;
	} else {
		return true;
	}
}

// 判断是否属于电话号码
String.prototype.isPhone = function(){
	if (this.trim().search(/^[0-9 | -]*$/)==-1) {// /^([0]?\d{2,3})?[-]?\d{5,8}([-]\d+)?$/
		return false;
	} else {
		return true;
	}
}

// 判断邮编
String.prototype.isPost = function(){
	if (this.trim().search(/^\d{6}$/)==-1) {
		return false;
	} else {
		return true;
	}
}

//判断邮编TW
String.prototype.isPostTW = function(){
	if (this.trim().search(/^\d{3,5}$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 判断日期(YYYY-MM-DD YYYY/MM/DD)
String.prototype.isDate = function(){
	var r = this.trim().match(/^(\d{4})-(\d{1,2})-(\d{1,2})$/);
	if( r == null ) {
		r = this.trim().match(/^(\d{4})\/(\d{1,2})\/(\d{1,2})$/);
		if(r == null ) {
			return false;
		}
	}
	var d = new Date(r[1], r[2]-1, r[3]);
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[2]&&d.getDate()==r[3]);
}

// 判断日期 (YYYY-MM-DD HH24:MI:SS 或者 YYYY/MM/DD HH24:MI:SS)
String.prototype.isDateTime = function() {
	var r = this.trim().match(/^(\d{4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
	if( r == null ) {
		r = this.trim().match(/^(\d{4})\/(\d{1,2})\/(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
		if(r == null ) {
			return false;
		}
		return false;
	}
	var d = new Date(r[1], r[2]-1, r[3], r[4], r[5], r[6]);
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[2]&&d.getDate()==r[3]
				&& d.getHours() == r[4] && d.getMinutes() == r[5] && d.getSeconds() == r[6]);
}

// 判断Email
String.prototype.isEmail = function(){
	if (this.trim().search(/^[a-zA-Z0-9]([a-zA-Z0-9]*[-_\.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\.][a-zA-Z0-9]*([\.][a-zA-Z0-9]*)?$/)==-1) {
		return false;
	} else {
		return true;
	}
}

// 是否中文字符
String.prototype.isNotCN = function() {
	if (this.trim().length == this.trim().replace(/[^\x00-\xff]/gi,'xx').length) {
		return true;
	} else {
		return false;
	}
}

// 是否为空
String.prototype.isNull = function(){
	if (this == null || this == "") {
		return true;
	} else {
		return false;
	}
}

//是否為百分數
String.prototype.isPercent = function(){
	if (parseFloat(this.trim()) >= 0.00 && parseFloat(this.trim()) <= 100.00) {
		return true;
	} else {
		return false;
	}
}

// 是否为URL
String.prototype.isUrl = function() {
    var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
        + '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@
        + '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184
        + '|' // 允许IP和DOMAIN（域名）
        + '([0-9a-z_!~*\'()-]+.)*' // 域名- www.
        + '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名
        + '[a-z]{2,6})' // first level domain- .com or .museum
        + '(:[0-9]{1,4})?' // 端口- :80
        + '((/?)|' // a slash isn't required if there is no file name
        + '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
    var re=new RegExp(strRegex);
    if (re.test(this.trim())) {
        return (true);
    } else {
        return (false);
    }
}

// 是否为数字
String.prototype.isNumber = function() {
	if(!isNaN(this.trim())) {
		return true;
	} else {
		return false;
	}
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d)\}/g,
		function(m,i){
			return args[i];
		}
	);
}

//第一种：（通过String对象的charCodeAt方法） 
String.prototype.getBytesLength = function() { 
	var length = 0; 
	for(i = 0;i < this.length; i++) { 
	iCode = this.charCodeAt(i); 
	if((iCode >= 0 && iCode <= 255) || (iCode >= 0xff61 && iCode <= 0xff9f)) { 
	length += 1; 
	} else { 
	length += 2; 
	} 
	} 
	return length; 
} 

String.prototype.replaceAll = function (AFindText,ARepText)
{
	raRegExp = new RegExp(AFindText,"g");
	return this.replace(raRegExp,ARepText);
}

/**
 * 检查字符串数组中是否存在重复项
 * @param array
 */
function isDumplicated(array){
	 return /(\x0f[^\x0f]+)\x0f[\s\S]*\1/.test( "\x0f " + array.join( "\x0f\x0f ") + "\x0f "); 
}

/*
*
*string:原始字符串
*substr:子字符串
*isIgnoreCase:忽略大小写
*/
function contains(string,substr,isIgnoreCase)
{
    if(isIgnoreCase)
    {
     string=string.toLowerCase();
     substr=substr.toLowerCase();
    }
     var startChar=substr.substring(0,1);
     var strLen=substr.length;
         for(var j=0; j<string.length-strLen+1; j++)
         {
             if(string.charAt(j)==startChar)//如果匹配起始字符,开始查找
             {
                   if(string.substring(j,j+strLen)==substr)//如果从j开始的字符与str匹配，那ok
                   {
                         return true;
                   }  
             }
         }
         return false;
}

