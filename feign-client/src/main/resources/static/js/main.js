$(document).ready(() => {

    $('#main').click(() => {
        let base_date = $('#base_date').val();
        let base_time = $('#base_time').val();
        let category = $('#category').val();

        let formData = {
            base_date: base_date,
            base_time: base_time,
            category: category,
        }


        $.ajax({
            type: 'GET',
            url: '/weather',
            contentType: 'application/json, charset=utf-8',
            data: $.param(formData),
            dataType: 'json',
            success: (response) => {
                alert("데이터 전송 완료!")
            },
            error: (error) => {
                alert("에러!")
            }
        })
    })

})