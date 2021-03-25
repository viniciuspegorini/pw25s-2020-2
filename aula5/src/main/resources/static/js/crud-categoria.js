"use strict";

function removerAjax(oFormElement, urlDestino) {
    if ( window.confirm("Você deseja remover o registro?!") ) {
        let http = new XMLHttpRequest();
        http.open(oFormElement.method, oFormElement.action, true);

        http.onreadystatechange = function () {
            alert("Status: " + http.readyState);

            if (http.readyState === 4) {
                if (http.status === 200) {
                    alert("Registro removido com sucesso!");
                } else {
                    alert("Falha ao remover registro!");
                }
            }
        }

        http.send(new FormData(oFormElement));
    }
    return false;
}

function removerJquery(id, url) {

    Swal.fire({
        title: 'Deseja remover o registro?!',
        text: 'Esta ação não poderá ser desfeita!!!',
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Remover',
        closeOnConfirm: false
        }).then((result) => {
            if (result.isConfirmed) {
                const destino = url + '/' + id;
                $.ajax({
                    type: 'DELETE',
                    url: destino,
                    success: function () {
                        Swal.fire({
                            title: 'Removido!',
                            text: 'Registro removido com sucesso!',
                            type: 'success'
                        }).then((result) => {
                            // window.location = '/categoria';
                            $('#row_' + id).remove();
                        });
                    },
                    error: function () {
                        Swal.fire({
                            title: 'Erro!',
                            text: 'Falha ao remover o registro!',
                            type: 'error'
                        });
                    }
                })
            }
    });

}



