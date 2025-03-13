let selectedFile = null;

$(document).ready(() => {
    checkToken();
    setUpAjax();
    getUserInfo().then((userInfo) => {

        $('#hiddenUserId').val(userInfo.userId);
        $('#hiddenUserName').val(userInfo.userName);
        $('#userId').val(userInfo.userId);
    }).catch((error) => {
        console.log('회원 정보 조회 오류 :' , error);
    });

    $('#file').change((event) => {
        selectedFile = event.target.files[0]
        updateFileList();
    });

    $('#submitBtn').click((event) => {
        event.preventDefault();

        let formData = new FormData($('#writeForm')[0]);

        $.ajax({
            type: 'POST',
            url: '/api/board',
            data: formData,
            contentType: false,
            processData: false,
            success: (response) => {
                alert('게시글 등록 완료!')
                window.location.href = '/';
            },
            error: (error) => {
                alert('게시글 등록 실패!')
                console.log('오류 :: ',error);
            }
        });
    });

    
});

let updateFileList = () => {
    $('#fileList').empty();

    if(selectedFile) {
        $('#fileList').append(
            `<li>
                ${selectedFile.name} <button type="button" class="remove-btn">X</button>
             </li>`
        );

        $('.remove-btn').click(() => {
            selectedFile = null;
            $('#file').val('');
            updateFileList();
        });
    }
}