document.addEventListener('DOMContentLoaded', () => {
    const loadingSpinner = document.getElementById('loading');
    const tasksContainer = document.getElementById('tasks');

    const fetchUsers = async () => {
        try {
            loadingSpinner.style.display = 'block'; // Exibe o carregador
            const response = await fetch('http://localhost:8080/usuario'); // Sua URL da API
            if (!response.ok) {
                throw new Error('Erro ao carregar os dados');
            }
            const users = await response.json(); // Converte a resposta para JSON
            return users;
        } catch (error) {
            console.error('Erro:', error);
            alert('Ocorreu um erro ao carregar os usuarios.');
        } finally {
            loadingSpinner.style.display = 'none'; // Oculta o carregador
        }
    };

    const renderUsers = (user) => {
        user.forEach(user => {
            const row = document.createElement('tr');
            const senhaOculta = '*'.repeat(user.senha.length); // Gera a string de asteriscos com o mesmo comprimento da senha
            row.innerHTML = `
                <td>${senhaOculta}</td> <!-- Exibe a senha oculta -->
                <td>${user.email}</td> <!-- Exemplo de campo adicional -->
                <td>${user.nome_usuario}</td>
            `;
            tasksContainer.appendChild(row);
        });
    };

    const loadUsers = async () => {
        const users = await fetchUsers();
        if (users) {
            renderUsers(users) // Renderiza os usuarios
        }
    };

    loadUsers(); // Chama a função para carregar usuarios
});
