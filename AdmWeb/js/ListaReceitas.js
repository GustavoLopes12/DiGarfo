document.addEventListener('DOMContentLoaded', () => {
    const loadingSpinner = document.getElementById('loading');
    const tasksContainer = document.getElementById('tasks');

    const fetchReceitas = async () => {
        try {
            loadingSpinner.style.display = 'block'; // Exibe o carregador
            const response = await fetch('http://localhost:8080/receita'); // Sua URL da API
            if (!response.ok) {
                throw new Error('Erro ao carregar os dados');
            }
            const Receitas = await response.json(); // Converte a resposta para JSON
            return Receitas;
        } catch (error) {
            console.error('Erro:', error);
            alert('Ocorreu um erro ao carregar as receitas.');
        } finally {
            loadingSpinner.style.display = 'none'; // Oculta o carregador
        }
    };

    const renderReceitas = (receitas) => {
        receitas.forEach(receita => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${receita.id_receita}</td> <!-- Exibe a senha oculta -->
                <td>${receita.aprovada}</td> <!-- Exemplo de campo adicional -->
                <td>${receita.categoria}</td> 
                <td>${receita.custo}</td> 
                <td>${receita.dificuldade}</td> 
                <td>${receita.img_receita}</td> 
                <td>${receita.ingredientes}</td> 
                <td>${receita.modo_prep}</td> 
                <td>${receita.nome_receita}</td> 
                <td>${receita.tempo_prep}</td> 
                <td>${receita.id_adm}</td> 
                <td>${receita.id_autor}</td> 

            `;
            tasksContainer.appendChild(row);
        });
    };

    const loadReceitas = async () => {
        const receitas = await fetchReceitas();
        if (receitas) {
            renderReceitas(receitas); // Renderiza as receitas
        }
    };

    loadReceitas(); // Chama a função para carregar receitas
});
