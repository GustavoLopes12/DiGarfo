document.addEventListener('DOMContentLoaded', function() {
    const recipeList = document.getElementById('recipeList');
    const recipes = JSON.parse(localStorage.getItem('recipes')) || [];

    recipes.forEach(function(recipe, index) {
        const li = document.createElement('li');
        li.id = `recipe-${index}`;
        li.innerHTML = `
            <h2>${recipe.recipeName}</h2>
            <p>Enviada por: ${recipe.username}</p>
            <img src="${recipe.recipeImage}" alt="${recipe.recipeName}" style="max-width: 200px;">
            <button onclick="evaluateRecipe(${index})">Avaliar</button>
            <button onclick="showReasonInput(${index})">Negar</button>
            <div id="reason-${index}" style="display:none;">
                <input type="text" placeholder="Motivo da negação" id="reasonInput-${index}">
                <button onclick="denyRecipe(${index})">Confirmar Negação</button>
            </div>
        `;
        recipeList.appendChild(li);
    });

    if (recipes.length === 0) {
        recipeList.innerHTML = '<p>Nenhuma receita enviada ainda.</p>';
    }
});

function showReasonInput(index) {
    const reasonDiv = document.getElementById(`reason-${index}`);
    reasonDiv.style.display = 'block';
}

function evaluateRecipe(index) {
    const recipes = JSON.parse(localStorage.getItem('recipes')) || [];
    alert(`Receita "${recipes[index].recipeName}" avaliada com sucesso!`);
    removeRecipe(index);
}

function denyRecipe(index) {
    const reasonInput = document.getElementById(`reasonInput-${index}`);
    const reason = reasonInput.value.trim();

    if (reason === "") {
        alert("Por favor, insira um motivo para a negação.");
        return;
    }

    const recipes = JSON.parse(localStorage.getItem('recipes')) || [];
    alert(`Receita "${recipes[index].recipeName}" negada. Motivo: ${reason}`);
    removeRecipe(index);
}

function removeRecipe(index) {
    let recipes = JSON.parse(localStorage.getItem('recipes')) || [];
    recipes.splice(index, 1); // Remove a receita pelo índice
    localStorage.setItem('recipes', JSON.stringify(recipes)); // Atualiza o local storage

    // Recarrega a lista de receitas
    location.reload();
}
    