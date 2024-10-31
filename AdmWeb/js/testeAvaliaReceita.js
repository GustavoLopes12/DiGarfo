document.addEventListener('DOMContentLoaded', () => {
    const loadingSpinner = document.getElementById('loading');
    const tasksContainer = document.getElementById('tasks');
    const errorMessage = document.getElementById('error-message');

    const fetchReceitas = async () => {
        try {
            loadingSpinner.style.display = 'block'; // Exibe o carregador
            const response = await fetch('http://localhost:8080/receita'); // Sua URL da API
            if (!response.ok) {
                throw new Error('Erro ao carregar os dados');
            }
            return await response.json(); // Converte a resposta para JSON
        } catch (error) {
            console.error('Erro:', error);
            errorMessage.textContent = 'Ocorreu um erro ao carregar as receitas.';
            errorMessage.style.display = 'block';
        } finally {
            loadingSpinner.style.display = 'none'; // Oculta o carregador
        }
    };

    const renderReceitas = (receitas) => {
        if (receitas.length === 0) {
            const row = document.createElement('tr');
            row.innerHTML = `<td colspan="13" class="text-center">Nenhuma receita encontrada.</td>`;
            tasksContainer.appendChild(row);
            return;
        }

        receitas.forEach(receita => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${receita.id_receita}</td>
                <td>${receita.aprovada}</td>
                <td>${receita.categoria}</td>
                <td>${receita.custo}</td>
                <td>${receita.dificuldade}</td>
                <td><img src="${receita.img_receita}" alt="${receita.nome_receita}" style="width: 50px; height: auto;"></td>
                <td>${receita.ingredientes}</td>
                <td>${receita.modo_prep}</td>
                <td>${receita.nome_receita}</td>
                <td>${receita.tempo_prep}</td>
                <td>${receita.adm}</td>
                <td>${receita.usuario}</td>
                <td>
                    <button class="btn btn-success approve" data-id="${receita.id_receita}">Aprovar</button>
                    <button class="btn btn-danger deny" data-id="${receita.id_receita}">Negado</button>
                </td>
            `;
            tasksContainer.appendChild(row);
        });

        attachEventListeners();
    };

    const attachEventListeners = () => {
        const approveButtons = document.querySelectorAll('.approve');
        const denyButtons = document.querySelectorAll('.deny');

        approveButtons.forEach(button => {
            button.addEventListener('click', async (event) => {
                const id = event.target.dataset.id;
                await approveReceita(id);
            });
        });

        denyButtons.forEach(button => {
            button.addEventListener('click', (event) => {
                const id = event.target.dataset.id;
                denyReceita(id);
            });
        });
    };

    const approveReceita = async (id) => {
        try {
            loadingSpinner.style.display = 'block'; // Exibe o carregador
            const response = await fetch(`http://localhost:8080/receita`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ aprovada: true }) // Atualiza o status da receita
            });

            if (!response.ok) {
                throw new Error('Erro ao aprovar a receita');
            }

            // Atualiza a interface
            loadReceitas();
        } catch (error) {
            console.error('Erro:', error);
            alert('Ocorreu um erro ao aprovar a receita.');
        } finally {
            loadingSpinner.style.display = 'none'; // Oculta o carregador
        }
    };

    const denyReceita = (id) => {
        const row = document.querySelector(`button[data-id="${id}"]`).closest('tr');
        tasksContainer.removeChild(row);
    };

    const loadReceitas = async () => {
        const receitas = await fetchReceitas();
        if (receitas) {
            renderReceitas(receitas); // Renderiza as receitas
        }
    };

    loadReceitas(); // Chama a função para carregar receitas
});
