document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    const messageDiv = document.getElementById('message');
    
    try {
        const response = await fetch('http://localhost:8080/adm/loginAdm', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email: email, senha: senha }),
        });

        if (response.ok) {
            messageDiv.textContent = 'Login bem-sucedido!';
            messageDiv.style.color = 'green';
            // Redirecionar ou executar outra ação
            window.location.href = 'home.html'; // Exemplo de redirecionamento
        } else {
            const errorData = await response.json();
            messageDiv.textContent = errorData.message || 'Credenciais inválidas!';
            messageDiv.style.color = 'red';
        }
    } catch (error) {
        console.error('Erro:', error);
        messageDiv.textContent = 'Ocorreu um erro ao realizar o login.';
        messageDiv.style.color = 'red';
    }
});
