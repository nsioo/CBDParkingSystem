
//使用post发送ajax时需要设置
Vue.http.options.emulateJSON = true;

const  app = new Vue({
    el:"#app",
    data:{
        /*姓名输入框*/
        stuName:"",
        /*年龄输入框*/
        stuAge:"",
        /*性别输入框*/
        stuGender:"男",
        /*保存返回后的json数据*/
        classesData:"",
        /*下拉菜单选中的value*/
        classId:"",
    },
    created:function(){
        /*当vue实例化后加载下拉菜单的值*/
        this.getClasses();
    },

    methods:{
        /*添加按钮事件*/
         add:function(){

             this.$http.post("../AddStudentServlet",{
                 /*后台传参数*/
                 stuName:this.stuName,
                 stuAge:this.stuAge,
                 stuGender: this.stuGender,
                 classId:this.classId
             }).then(function(result){
            // console.log(result.body+"***");
                 if(result.body==1){
                     this.$alert('添加数据成功',{
                         title:"消息提示",
                         confirmButtonText: '确定',
                         type:'success',
                         center: true
                     });
                     window.location.href="studentList.html";
                 }else{
                     this.$alert('添加失败', {
                         title:"消息提示",
                         confirmButtonText: '确定',
                         type:'error',
                         center: true
                     });
                 }
                 });
         },
        /*取消按钮事件*/
        cancle:function(){
             window.history.go(-1);
        },
        /*获得所有班级数据*/
        getClasses:function(){
             this.$http.post("../GetAllClassesServlet")
                  .then(function(result){
                 console.log(result.body+"---->>");
                     this.classesData=result.body;
                  });
        }
    }

});
