$(document).ready(() => {

    $('#signup').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();
        let userName = $('#user_name').val();
        let role = $('#role').val();

        let formData = {
            userId: userId,
            password: password,
            userName: userName,
            role: role
        }

        console.log('formData ::', formData);

        $.ajax({
            type: 'POST',
            url: '/join',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            success: (response) => {
                alert("회원가입 성공!\n로그인 해주세요!")
                if(response.success){
                    window.location.href = '/member/login'
                }
            },
            error: (error) => {
                console.log('error::' , error)
                alert("회원가입 실패!")
            }
        });
    });

});