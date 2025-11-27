const ingredientsContainer = document.getElementById("ingredients");
const addIngredientButton = document.getElementById("addIngredient");

addIngredientButton.addEventListener("click", function () {
    const ingredientLine = document.createElement("div");
    ingredientLine.innerHTML = `
      <div class="row g-3">
        <div class="col-md-2">
            <input type="text" class="form-control" name="ingredientNames[]" placeholder="Ingredient">
        </div>
        <div class="col-md-2">
            <input type="text" class="form-control" name="quantities[]" placeholder="Quantity">
        </div>
        <div class="col-md-2">
            <input type="text" class="form-control" name="units[]" placeholder="Unit">
        </div>
        <div class="col-md-2">
            <input type="number" class="form-control" name="quantitiesInGrams[]">
        </div>
        <div class="col-md-2">
            <input type="number" class="form-control" name="kcalPer100g[]">
        </div>
        <div class="col">
            <button type="button" class="remove"><i class="bi bi-trash-fill"></i>Remove</button>
        </div>
      </div>
    `;
    ingredientsContainer.appendChild(ingredientLine);
});

const instructionsContainer = document.getElementById("instructions");
const addInstructionButton = document.getElementById("addInstruction");
let count = parseInt(instructionsContainer.dataset.count);

addInstructionButton.addEventListener("click", function () {
    const instructionLine = document.createElement("div");

    instructionLine.innerHTML = `
        <div class="row g-3">
            <div class="col-md-10">
                <input type="text" class="form-control" placeholder="Add a step" name="instructions[${count}]">
            </div>
            <div class="col">
                <button type="button" class="remove"><i class="bi bi-trash-fill"></i>Remove</button>
            </div>
          </div>
    `;

    instructionsContainer.appendChild(instructionLine);

    count++;
});

ingredientsContainer.addEventListener("click", removeClick);
instructionsContainer.addEventListener("click", removeClick);

function removeClick(e) {
    if (e.target.classList.contains("remove")) {
    e.target.parentElement.parentElement.remove();
    }
}