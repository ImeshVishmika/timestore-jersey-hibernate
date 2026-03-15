document.getElementById("messages-tab").addEventListener("click", loadMsgSenders);
var messageButtons;

async function loadMsgSenders() {
    try {
        const request = await fetch("/timestore/api/message/senders", {
            method: "POST"
        });

        if (request.ok) {
            const jsonObject = await request.json();
            const messageTableBody = document.getElementById("msgSenderTableBody");
            messageTableBody.innerHTML = "";

            const fragment = document.createDocumentFragment();

            jsonObject.forEach(user => {
                const div = document.createElement("div");
                div.className = "d-flex align-items-center p-2 mb-2 border-bottom hover-bg message_item";
                div.dataset.email = user.sender;
                div.dataset.first_name = user.fname;
                div.dataset.last_name = user.lname;
                div.dataset.new_msg = user.new_msg;
                div.innerHTML = `
    <div class="position-relative">
        <img src="https://ui-avatars.com/api/?name=John+Doe&background=dc3545&color=fff" class="rounded-circle me-3" width="45">
    </div>

    <div class="flex-grow-1 overflow-hidden">   
        <h6 class="mb-0 fw-bold text-dark text-truncate">${user.fname} ${user.lname}</h6>
         <small class="text-secondary d-block text-truncate">${user.sender}</small>
        </div>

    <div class="d-flex flex-column align-items-end ms-2">
        
        <small class="fw-bold text-secondary mb-1" style="font-size: 11px;">${user.date}</small>
         ${user.new_msg > 0 ? `
        <span class="badge rounded-pill text-bg-primary">
           ${user.new_msg}
        </span>` : ``}

    </div>`;
                fragment.appendChild(div);
            });
            messageTableBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch messages');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

document.getElementById("msgSenderTableBody").addEventListener("click", (event) => {
    var button = event.target.closest(".message_item");
    if (button) {
        loadMessageItems(button.dataset.email);
    }
});

async function loadMessageItems(email) {
    try {
        const form = new FormData();
        form.append("sender", email);

        const request = await fetch("/timestore/api/message/userMessages", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            const jsonObject = await request.json();

            document.getElementById("newMsgCount").textContent = jsonObject.sender.new_msg > 0 ? jsonObject.sender.new_msg + " new messages" : "No new messages";
            document.getElementById("msgSender").textContent = jsonObject.sender.fname + " " + jsonObject.sender.lname;

            const userMsgTableBody = document.getElementById("userMsgTableBody");
            userMsgTableBody.innerHTML = "";

            const fragment = document.createDocumentFragment();

            jsonObject.messages.forEach(message => {
                const div = document.createElement("div");

                div.className = "col-12";
                div.innerHTML = `
                    <div class="card msg-card shadow-sm rounded-3 border-0" 
                        data-subject="${message.subject}"
                        data-message_id="${message.message_id}"
                        data-message="${message.message}"
                        data-date="${message.date}"
                        data-time="${message.time}"
                        data-name="${jsonObject.sender.fname} ${jsonObject.sender.lname}"
                        data-sender="${jsonObject.sender.email}"
                        data-status="${message.status}"
                        >
                                                <div class="card-body p-4">
                                                    <div class="d-flex justify-content-between mb-2">
                                                        <h6 class="fw-bold ${message.status === '1' ? `text-secondary` : `text-dark`} mb-0">${message.subject}</h6>
                                                        <small class="text-muted">${message.date}</small>
                                                    </div>
                                                    <p class="text-muted small mb-0 text-truncate">
                                                        ${message.message}
                                                    </p>
                                                </div>
                                            </div>
                `;
                div.dataset.bsToggle = "modal";
                div.dataset.bsTarget = "#readMessageModal";

                fragment.appendChild(div);
            });
            userMsgTableBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch messages');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

document.getElementById("userMsgTableBody").addEventListener("click", async (event) => {
    const card = event.target.closest(".msg-card");

    if (!card) {
        return;
    }

    document.getElementById("msgModalSubject").textContent = card.dataset.subject;
    document.getElementById("msgModalSender").textContent = card.dataset.name;
    document.getElementById("msgModalEmail").textContent = card.dataset.sender;
    document.getElementById("msgModalDate").textContent = card.dataset.date;
    document.getElementById("msgModalContent").textContent = card.dataset.message;
    document.getElementById("msgModalTime").textContent = card.dataset.time;

    if (card.dataset.status == 2) {
        try {
            const form = new FormData();
            form.append("message_id", card.dataset.message_id);

            const request = await fetch("/timestore/api/message/changeState", {
                method: "POST",
                body: form
            });

            if (request.ok) {
                const h6 = card.querySelector("h6");
                h6.classList.remove("text-dark");
                h6.classList.add("text-secondary");
                loadMsgSenders();
                loadMessageItems(card.dataset.sender);
            } else {
                Notiflix.Notify.failure('Failed to update message status');
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    }

});