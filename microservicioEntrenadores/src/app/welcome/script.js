document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('cambiarTexto').addEventListener('click', function() {
        document.getElementById('textoCambiado').innerText = '¡El texto ha sido cambiado!';
    });
});