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