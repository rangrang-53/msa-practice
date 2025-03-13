let selectedFile = null;

$(document).ready(() => {
    checkToken();
    setUpAjax();
    getUserInfo().then((userInfo) => {
        $('#hiddenUserName').val(userInfo.userName);
        $('#hiddenUserId').val(userInfo.userId);
        $('#userId').val(userInfo.userId);
    }).catch((error) => {
        console.log('회원 정보 조회 실패 :: ', error);
    });
    loadBoardDetail();

    $('#hiddenFileFlag').val(false);
    $('#file').change((event) => {
        selectedFile = event.target.files[0];
        $('#hiddenFileFlag').val(true);
        updateFileList();
    });

    $('#submitBtn').click((event) => {
        event.preventDefault();

        let formData = new FormData($('#writeForm')[0]);

        $.ajax({
            type: 'PUT',
            url: '/api/board',
            data: formData,
            contentType: false,
            processData: false,
            success: (response) => {
                alert('게시물이 성공적으로 수정되었습니다!')
                window.location.href = '/'
            },
            error: (error) => {
                console.log(error);
                alert('게시물 수정 중 오류가 발생했습니다!')
            }
        });
    });

});

let updateFileList = () => {
    $('#fileList').empty();
    if (selectedFile) {
        $('#fileList').append(`
            <li>
                ${selectedFile.name} <button type="button" class ="remove-btn">X</button>
            </li>
        `);
        $('.remove-btn').click(() => {
            selectedFile = null;
            $('#file').val('');
            updateFileList();
        });
    }
}

let loadBoardDetail = () => {
    let hId = $('#hiddenId').val();
    $.ajax({
        type: 'GET',
        url: '/api/board/' + hId,
        success: (response) => {
            console.log(response);
            $('#title').val(response.title);
            $('#content').val(response.content);
            $('#userId').val(response.userId);

            if(response.filePath && response.filePath.length > 0) {
                let filePath = response.filePath;
                $('#hiddenFilePath').val(filePath);

                let fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);

                let fileElement = `
                            <li>
                                <a href="/api/board/file/download/${fileName}">${fileName}</a> <!-- 다운로드 링크 -->
                            </li>`;
                $('#fileList').append(fileElement);

                $('.remove-btn').click(() => {
                    selectedFile = null;
                    $('#file').val('');
                    updateFileList();
                });
            } else {
                $('#fileList').append('<li>첨부된 파일이 없습니다.</li>')
            }
        },
        error: (error) => {
            console.log('불러오기 오류 :: ', error);
        }
    });
}