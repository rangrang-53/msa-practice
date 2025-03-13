$(document).ready(() => {

    $('#signin').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
            username: userId,
            password: password,
        }

        console.log(formData);

        $.ajax({
            type: 'POST',
            url: '/login',
            data: JSON.stringify(formData),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (response) => {
                alert('로그인 성공!')
                localStorage.setItem('accessToken',response.token)
                window.location.href = '/'
            },
            error: (error) => {
                console.log('오류 발생 ::', error);
                alert('로그인 실패!')
            }
        });
    });
});