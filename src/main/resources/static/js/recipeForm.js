const addedInstructions = document.getElementById("addedInstructions");
const existingInstructions = document.getElementById("existingInstructions")

addedInstructions.addEventListener("click", removeClick)
existingInstructions.addEventListener("click", removeClick)

function removeClick(e) {
    if (e.target.classList.contains("removeButton")) {
    e.target.parentElement.remove();
    }
}

let count = parseInt(addedInstructions.dataset.count);

function addInput() {
    const container = document.createElement("div")

    const input = document.createElement("input");
    input.type = "text";
    input.placeholder = "Next step";
    input.name = `instructions[${count}]`

    const removeButton = document.createElement("button")
    removeButton.type = "button"
    removeButton.textContent = "remove"
    removeButton.className = "removeButton"

    addedInstructions.append(container)
    container.append(input, removeButton, document.createElement("br"));

    count++;
}

document.addEventListener("DOMContentLoaded", function () {
    const ingredientsContainer = document.getElementById("ingredients");
    const addButton = document.getElementById("addIngredient");

    addButton.addEventListener("click", function () {
        const div = document.createElement("div");
        div.classList.add("ingredient");
        div.innerHTML = `
          <input type="text" name="ingredientNames[]" placeholder="Ingredient">
          <input type="text" name="amounts[]" placeholder="Amount">
          <button type="button" class="remove">Remove</button>
        `;
        ingredientsContainer.appendChild(div);
    });

    ingredientsContainer.addEventListener("click", function (e) {
        if (e.target.classList.contains("remove")) {
            e.target.closest(".ingredient").remove();
        }
    });
});