const ingredientsContainer = document.getElementById("ingredients");
const addIngredientButton = document.getElementById("addIngredient");

addIngredientButton.addEventListener("click", function () {
    const ingredientLine = document.createElement("div");
    ingredientLine.innerHTML = `
      <input type="text" name="ingredientNames[]" placeholder="Ingredient">
      <input type="text" name="amounts[]" placeholder="Amount">
      <button type="button" class="remove">Remove</button>
    `;
    ingredientsContainer.appendChild(ingredientLine);
});

const instructionsContainer = document.getElementById("instructions");
const addInstructionButton = document.getElementById("addInstruction");
let count = parseInt(instructionsContainer.dataset.count);

addInstructionButton.addEventListener("click", function () {
    const instructionLine = document.createElement("div");

    const input = document.createElement("input");
    input.type = "text";
    input.placeholder = "Add a step";
    input.name = `instructions[${count}]`;

    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.textContent = "Remove";
    removeButton.className = "remove";

    instructionLine.append(input, removeButton);
    instructionsContainer.appendChild(instructionLine);
});

ingredientsContainer.addEventListener("click", removeClick);
instructionsContainer.addEventListener("click", removeClick);

function removeClick(e) {
    if (e.target.classList.contains("remove")) {
    e.target.parentElement.remove();
    }
}