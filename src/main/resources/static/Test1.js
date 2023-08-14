// Define the API endpoint
const API_BASE_URL = 'http://localhost:8080/api';

// Function to set the project name
function setProjectName() {
    var projectName = document.getElementById('projectName').value;

    document.getElementById('displayProjectName').textContent = "Project Name: " + projectName;
    createNewBoard();
}
 async function createNewBoard() {
  try {
    // Get the board title from the input field
    const projectName = document.getElementById('projectName').value;

    // Prepare the request body
    const requestBody = {
      title: projectName,
    };

    // Configure the request options
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody),
      redirect: 'follow',
    };

    // Send the POST request to create a new board
    const response = await fetch('http://localhost:8080/api/boards', requestOptions);

    if (!response.ok) {
      throw new Error(`Failed to create board. Status: ${response.status} ${response.statusText}`);
    }

    // Extract the newly created board data
    const newBoard = await response.json();
    console.log('New Board:', newBoard);

    // Clear the input field after successful creation
    document.getElementById('titleInput').value = '';


  } catch (error) {
    console.error('Error creating board:', error);
    alert('An error occurred while creating the board.');
  }
}

// Function to create an HTML element for a task
function createTaskElement(task) {
    var taskElement = document.createElement('div');
    taskElement.classList.add('task');
    taskElement.setAttribute('data-task-id', task.card_id);
    taskElement.innerHTML = `
        <div class="task-info">
            <strong>ID:</strong> ${task.card_id}
        </div>
        <div class="task-info">
            <strong>Title:</strong> ${task.title}
        </div>
        <div class="task-info">
            <strong>Description:</strong> ${task.description}
        </div>
    `;
    return taskElement;
}

// Function to add a task element to a section
function addToSection(sectionId, taskElement) {
    var section = document.getElementById(sectionId);
    section.appendChild(taskElement);
}

// Function to fetch and display cards
async function fetchAndDisplayCards() {
    try {
        const response = await fetch(`${API_BASE_URL}/boards/1/cards`);
        const data = await response.json();

        if (response.ok) {
            const cards = data.cards;

            cards.forEach(card => {
                // const cardElement = createTaskElement(card);
                // addToSection(`section${card.section}`, cardElement);

                let boardDiv;

                let getSectionId = card.section;
                if (getSectionId === 1) {
                    boardDiv = document.getElementById("section1")
                }
                else if (getSectionId === 2) {
                    boardDiv = document.getElementById("section2")
                }
                else {
                    boardDiv = document.getElementById("section3")
                }

                let cardDiv = document.createElement("div");
                cardDiv.className = "task";

                let idDiv = document.createElement("div");
                idDiv.className = "idDiv";
                idDiv.textContent = card.card_id;

                let titleDiv = document.createElement("div");
                titleDiv.className = "titleDiv";
                titleDiv.textContent = card.title;

                let descriptionDiv = document.createElement("div");
                descriptionDiv.className = "descriptionDiv";
                descriptionDiv.textContent = card.description;

                cardDiv.appendChild(idDiv);
                cardDiv.appendChild(titleDiv);
                cardDiv.appendChild(descriptionDiv);

                boardDiv.appendChild(cardDiv);


            });
        } else {
            console.error('Error fetching cards:', data.message);
        }
    } catch (error) {
        console.error('Error fetching cards:', error);
    }
}

// Call the fetchAndDisplayCards function to populate the sections
fetchAndDisplayCards();

//--------------------------------------------------------

// Function to create a new card
function createCard1() {

    const selectElement = document.getElementById("cardStatus");
    var selectedIndex = selectElement.selectedIndex;
    var selectedValue = selectElement.options[selectedIndex].value;

    if (selectedValue === "todo") {
        cardStatus = 1;
    }
    else if (selectedValue === "inProgress") {
        cardStatus = 2;
    }
    else {
        cardStatus = 3;
    }


    const cardName = document.getElementById('cardName').value;
    const cardDescription = document.getElementById('cardDescription').value;

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "title": cardName,
        "description": cardDescription,
        "section": cardStatus
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/api/boards/1/cards", requestOptions)
        .then(response => response.json())
        .then(result => location.reload())
        .catch(error => console.log('error', error));
}

function deleteCard() {
    let cardDeleteId = document.getElementById("deleteTaskId").value;

    var requestOptions = {
        method: 'DELETE',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/api/boards/1/cards/" + cardDeleteId, requestOptions)
        .then(response => {
            response.json();
            location.reload();
        })
        .then(result => location.reload())
        .catch(error => console.log('error', error));
}

// Attach the createCard function to the "Add Card" button's click event
// const addCardButton = document.querySelector('#cardForm button');
// addCardButton.addEventListener('click', createCard1); // Use createCard1

function updateCard() {
    var selectedValue = document.getElementById("updateTaskId").value;
    if (!selectedValue) {
        alert("Select a task to update.");
    } else {
        var titleValue = document.getElementById('updateCardTitle').value;
        var descriptionValue = document.getElementById('updateCardDescription').value;
        var sectionValue = document.getElementById('updateCardStatus').value;

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        var raw = JSON.stringify({
            "title": titleValue,
            "description": descriptionValue,
            "section": sectionValue // Corrected field name
        });

        var requestOptions = {
            method: 'PUT',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch(`${API_BASE_URL}/boards/1/cards/${selectedValue}`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result);
                // Refresh the page after updating
                location.reload();
            })
            .catch(error => console.log('error', error));
    }
}



