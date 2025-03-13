$(document).ready(() => {

    $('#signup').click(() => {
        let id = $('#id').val();
        let password = $('#password').val();
        let nickname = $('#nickname').val();

        console.log('user id ::', id);
        console.log('user pw ::', password);
        console.log('user nickname ::', nickname);

        let formdata = {
            id: id,
            password: password,
            nickname: nickname
        }

        $.ajax({
            type: 'POST',
            url: '/join',
            data: JSON.stringify(formdata),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (response) => {
                alert('회원가입이 성공했습니다!\n로그인해주세요.');
            },
            error: (error) => {
                console.log('오류발생: ', error);
                alert('회원가입 중 오류가 발생하였습니다.');

            }
        })

    });
});