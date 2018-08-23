 var HtmlUtil = {
      /*1.用浏览器内部转换器实现html转码*/
      htmlEncode:function (html){
          //1.首先动态创建一个容器标签元素，如DIV
          var temp = document.createElement ("div");
          //2.然后将要转换的字符串设置为这个元素的innerText(ie支持)或者textContent(火狐，google支持)
          (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
          //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
          var output = temp.innerHTML;
         temp = null;
         return output;
     },
     /*2.用浏览器内部转换器实现html解码*/
     htmlDecode:function (text){
         //1.首先动态创建一个容器标签元素，如DIV
         var temp = document.createElement("div");
         //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
         temp.innerHTML = text;
         //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
         var output = temp.innerText || temp.textContent;
         temp = null;
         return output;
     },
     /*3.用正则表达式实现html转码*/
     htmlEncodeByRegExp:function (str){  
          var s = "";
          if(str.length == 0) return "";
          s = str.replace(/&/g,"&amp;");
          s = s.replace(/</g,"&lt;");
          s = s.replace(/>/g,"&gt;");
          s = s.replace(/ /g,"&nbsp;");
          s = s.replace(/\'/g,"&#39;");
          s = s.replace(/\"/g,"&quot;");
          return s;  
    },
    /*4.用正则表达式实现html解码*/
    htmlDecodeByRegExp:function (str){  
          var s = "";
          if(str.length == 0) return "";
          s = str.replace(/&amp;/g,"&");
          s = s.replace(/&lt;/g,"<");
          s = s.replace(/&gt;/g,">");
          s = s.replace(/&nbsp;/g," ");
          s = s.replace(/&#39;/g,"\'");
          s = s.replace(/&quot;/g,"\"");
          return s;  
    }
 };
 
 
 var BrowserUtil = {info:function (){ 
	    var Browser={name:"",version:""}; 
	    var ua=navigator.userAgent.toLowerCase(); 
	    var s; 
	    (s=ua.match(/msie ([\d.]+)/))?Browser.ie=s[1]: 
	    (s=ua.match(/firefox\/([\d.]+)/))?Browser.firefox=s[1]: 
	    (s=ua.match(/chrome\/([\d.]+)/))?Browser.chrome=s[1]: 
	    (s=ua.match(/opera.([\d.]+)/))?Browser.opera=s[1]: 
	    (s=ua.match(/version\/([\d.]+).*safari/))?Browser.safari=s[1]:0; 
	    if(Browser.ie){//Js判断为IE浏览器 
	    	Browser.name ="IE";
	    	Browser.version = Browser.ie;
	        return Browser;
	    } 
	    if(Browser.firefox){//Js判断为火狐(firefox)浏览器 
	    	Browser.name ="Firefox";
	    	Browser.version = Browser.firefox;
	        return Browser;
	    } 
	    if(Browser.chrome){//Js判断为谷歌chrome浏览器 
	    	Browser.name ="Chrome";
	    	Browser.version = Browser.chrome;
	        return Browser;
	    } 
	    if(Browser.opera){//Js判断为opera浏览器 
	        Browser.name ="Opera";
	    	Browser.version = Browser.opera;
	        return Browser;
	    } 
	    if(Browser.safari){//Js判断为苹果safari浏览器 
	    	Browser.name ="Safari";
	    	Browser.version = Browser.safari;
	        return Browser;
	    } 
	    return Browser;
	}
 
 }