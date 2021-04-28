"use strict";
function remover(id, url) {
    Swal.fire({
        title: "Deseja realmente remover o registro?!",
        text: "Esta ação não poderá ser desfeita!",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        cancelButtonText: "Cancelar",
        confirmButtonText: "Remover",
        closeOnConfirm: false
    }).then((result) => {
            if (result.isConfirmed) {
                const destino = url + '/' + id;
                $.ajax({
                    type: 'DELETE',
                    url: destino,
                    success: function(){
                        Swal.fire({
                            title: 'Removido!',
                            text: 'Registro removido com sucesso!',
                            type: 'success'
                        }).then((result) => {
                                // window.location = url;
                                $('#row_' + id).remove();
                            }
                        ); //FIM swal()
                    },
                    error: function() {
                        Swal.fire('Erro!',
                            'Falha ao remover registro!',
                            'error');
                    }
                }); //FIM ajax()
            }
        } //FIM funcion swal
    ); //FIM swal();
}