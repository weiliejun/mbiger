/**
 * @Description Common包含表单加载公共方法。
 */
var Common = function () {

    // 初始化表格
    var initTable = function (ele, url, cols, table, doneCallBack) {
        return table.render({
            elem: ele,
            height : 660,
            width: 1650,
            limit : 10,
            limits : [10,15,20,25],
            url: PageContext.getUrl(url),
            method:'POST',
            request: {
                pageName: 'currentPage'
                ,limitName: 'pageSize'
            },
            response: { //定义后端 json 格式
                statusName: 'flag', //状态字段名称
                statusCode: 'true', //状态字段成功值
                msgName: 'msg', //消息字段
                countName: 'count', //总页数字段
                dataName: 'data' //数据字段
            },
            where:getParams('searchForm'),
            page: true,
            even: true,
            cols: cols,
            done: function (res, curr, count) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    };

    // 条件查询
    var searchTable = function (formId, tableIns) {
        var index = layer.msg('查询中，请稍候...',{icon: 16,time:false,shade:0});
        var queryParams = getParams(formId);
        tableIns.reload({
            where: queryParams,
            page: {
                curr: 1 //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                layer.close(index);
            }
        });
        return false;
    };

    // 清空表单
    var searchTableClear = function (formId) {
        var $ = layui.jquery;
        $(':input', '#searchForm')
            .not(':button, :submit, :reset, :hidden,:radio') // 去除不需要重置的input类型
            .val('')
            .removeAttr('checked')
            .removeAttr('selected');
        // 清空下拉列表
        $('#searchForm select').find("option").removeAttr('selected');
    };

    // 获取表单参数
    var getParams = function (formId) {
        var $ = layui.jquery;
        var _params = {};
        $.each($('#' + formId).serializeArray(), function (i, field) {
            if (null != field.value && "" != field.value) {
                _params[field.name] = field.value;
            }
        });
        return _params;
    };

    // 上传
    var upload = function (eleId, layUpload, done, error, accept, exts) {
        layUpload.render({
            elem: eleId //绑定元素
            , url: '/upload/' //上传接口
            , accept: accept === undefined ? 'file' : accept
            , exts: exts === undefined ? 'jpg|png|gif|bmp|jpeg' : exts
            , done: function (res) {
                //上传完毕回调
                if (typeof (done) === 'function') {
                    done(res)
                }
            }
            , error: function () {
                //请求异常回调
                if (typeof (error) === 'function') {
                    error()
                }
            }
        });
    };

    // 弹出frame弹框
    var openFrame = function (url, title, width, height) {
        width = width === undefined ? '900px' : width;
        height = height === undefined ? '500px' : height;
        return this.layer.open({
            title: title,
            type: 2,
            skin: 'layui-layer-molv',
            area: [width, height],
            content: PageContext.getUrl(url) //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    };

    // 确认弹框
    var confirmFrame = function (url, title, data, ele, table) {
        var $ = layui.jquery;
        return this.layer.confirm(title, function(index) {
            var ajaxReturnData;
            $.ajax({
                url: PageContext.getUrl(url),
                type: 'post',
                async: false,
                data: data,
                success: function (data) {
                    ajaxReturnData = data;
                }
            });
            if (ajaxReturnData.flag == 'true') {
                table.reload(ele);
                layer.msg(ajaxReturnData.msg, {icon: 1});
            } else {
                layer.msg(ajaxReturnData.msg, {icon: 5});
            }
            layer.close(index);
        });
    }

    return {
        initTable: function (ele, url, cols, table, doneCallBack) {
            return initTable(ele, url, cols, table, doneCallBack);
        },
        searchTable: function (formId, table) {
            searchTable(formId, table);
        },
        searchTableClear: function (formId) {
            searchTableClear(formId);
        },
        uploadFile: function (eleId, layUpload, done, error, accept, exts) {
            upload(eleId, layUpload, done, error, accept, exts);
        },
        openFrame: function (url, title, width, height) {
            return openFrame(url, title, width, height);
        },
        confirmFrame: function (url, title, data, ele, table) {
            return confirmFrame(url, title, data, ele, table);
        }
    }
}();

/**对Date的扩展，将 Date 转化为指定格式的String
 *  月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 *  年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 *例子：
 *     (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 *     (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 */
Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}