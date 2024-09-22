$(()=>{
    console.log('hello km')

    $('#btn').on('click',()=>{
        $.ajax({
            url: '/admin/system/dict/type/list',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                groupCode: "system"
            }),
            success: function(response) {
                console.log('成功接收到响应:', response);
            },
            error: function(xhr, status, error) {
                console.error('请求失败:', status, error);
            }
        });
    })


})