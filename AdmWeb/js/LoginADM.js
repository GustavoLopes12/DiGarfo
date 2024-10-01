console.log("LoginADM.js carregado");
function enviar(){
    
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    try {
        const response = fetch('http://localhost:8080/adm/loginAdm', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, senha }),
        });

        if (!response.ok) {
            throw new Error('Login falhou!');
        }

        const data = response.json();
        // Armazenar token ou informações do usuário
        console.log('Login bem-sucedido!', data);
        document.getElementById('message').innerText = 'Login bem-sucedido!';

         // Redirecionar para home.html
         window.location.href = 'home.html';

        // Redirecionar ou atualizar a interface
    } catch (error) {
        console.error('Erro:', error);
        document.getElementById('message').innerText = 'Erro ao fazer login.';
    }
}
