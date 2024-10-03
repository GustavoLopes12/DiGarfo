document.addEventListener('DOMContentLoaded', () => {
    const loadingSpinner = document.getElementById('loading');
    const tasksContainer = document.getElementById('tasks');

    const fetchAdmins = async () => {
        try {
            loadingSpinner.style.display = 'block'; // Exibe o carregador
            const response = await fetch('http://localhost:8080/adm'); // Sua URL da API
            if (!response.ok) {
                throw new Error('Erro ao carregar os dados');
            }
            const admins = await response.json(); // Converte a resposta para JSON
            return admins;
        } catch (error) {
            console.error('Erro:', error);
            alert('Ocorreu um erro ao carregar os administradores.');
        } finally {
            loadingSpinner.style.display = 'none'; // Oculta o carregador
        }
    };

    const renderAdmins = (admins) => {
        admins.forEach(admin => {
            const row = document.createElement('tr');
            const senhaOculta = '*'.repeat(admin.senha.length); // Gera a string de asteriscos com o mesmo comprimento da senha
            row.innerHTML = `
                <td>${senhaOculta}</td> <!-- Exibe a senha oculta -->
                <td>${admin.email}</td> <!-- Exemplo de campo adicional -->
            `;
            tasksContainer.appendChild(row);
        });
    };

    const loadAdmins = async () => {
        const admins = await fetchAdmins();
        if (admins) {
            renderAdmins(admins); // Renderiza os administradores
        }
    };

    loadAdmins(); // Chama a função para carregar administradores
});
