document.getElementById('installButton').addEventListener('click', function() {
    const button = this;
    
    // Muda o texto e desabilita o botão durante o "carregamento"
    button.textContent = 'Instalando...';
    button.disabled = true;

    // Simula um tempo de instalação
    setTimeout(function() {
        alert('App instalado com sucesso!');
        button.textContent = 'Instalar';
        button.disabled = false;
    }, 2000); // 2 segundos
});
