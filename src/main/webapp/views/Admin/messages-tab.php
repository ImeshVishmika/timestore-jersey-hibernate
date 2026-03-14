<div class="tab-pane" id="msg" role="tabpanel" aria-labelledby="msg-tab" tabindex="0">
    <div class="row g-0 h-100 min-vh-100">
        <div class="row">

            <div class=" col-6 col-lg-3   sidebar-col px-0">

                <div class="p-4 border-bottom sticky-top bg-white z-1">
                    <h5 class="fw-bold mb-3">Inbox</h5>
                    <div class="input-group">
                        <span class="input-group-text bg-light border-end-0"><i class="bi bi-search text-secondary"></i></span>
                        <input type="text" class="form-control bg-light border-start-0" placeholder="Search customer...">
                    </div>
                </div>


                <div id="msgSenderTableBody" class="list-group list-group-flush">

                    <div class="list-group-item p-3 user-card active">
                        <div class="d-flex align-items-center">
                            <div class="position-relative">
                                <img src="https://ui-avatars.com/api/?name=John+Doe&background=dc3545&color=fff" class="rounded-circle me-3" width="45">
                                <span class="position-absolute bottom-0 start-100 translate-middle p-1 bg-success border border-light rounded-circle"></span>
                            </div>
                            <div class="flex-grow-1 overflow-hidden">
                                <h6 class="mb-0 fw-bold text-dark text-truncate">John Doe</h6>
                                <small class="text-secondary d-block text-truncate">john.doe@gmail.com</small>
                            </div>
                            <small class="fw-bold text-dark">10:30</small>
                        </div>
                    </div>

                    <div class="list-group-item p-3 user-card">
                        <div class="d-flex align-items-center">
                            <div class="position-relative">
                                <img src="https://ui-avatars.com/api/?name=Sarah+Connor&background=212529&color=fff" class="rounded-circle me-3" width="45">
                            </div>
                            <div class="flex-grow-1 overflow-hidden">
                                <h6 class="mb-0 fw-bold text-dark text-truncate">Sarah Connor</h6>
                                <small class="text-secondary d-block text-truncate">sarah@skynet.com</small>
                            </div>
                            <small class="text-muted">Yesterday</small>
                        </div>
                    </div>

                    <div class="list-group-item p-3 user-card">
                        <div class="d-flex align-items-center">
                            <div class="position-relative">
                                <img src="https://ui-avatars.com/api/?name=Bruce+Wayne&background=0d6efd&color=fff" class="rounded-circle me-3" width="45">
                            </div>
                            <div class="flex-grow-1 overflow-hidden">
                                <h6 class="mb-0 fw-bold text-dark text-truncate">Bruce Wayne</h6>
                                <small class="text-secondary d-block text-truncate">bruce@wayne.com</small>
                            </div>
                            <small class="text-muted">Jan 28</small>
                        </div>
                    </div>

                </div>
            </div>


            <div class="col-6 col-lg-9 content-col p-4">

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h4 class="fw-bold mb-0" id="msgSender">John Doe</h4>
                        <p class="text-muted small mb-0" id="newMsgCount"></p>
                    </div>
                    <button class="btn btn-outline-dark btn-sm rounded-pill px-3"><i class="bi bi-envelope-plus me-2"></i>New Message</button>
                </div>

                <div id="userMsgTableBody" class="row g-3">


                </div>

                <div class="modal fade" id="readMessageModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
                        <div class="modal-content rounded-4 border-0 shadow-lg">

                            <div class="modal-header border-bottom-0 p-4 pb-0">
                                <div class="w-100 pe-3">
                                    <span class="badge bg-danger bg-opacity-10 text-danger mb-2 px-3 rounded-pill">Customer Inquiry</span>
                                    <h4 class="modal-title fw-bold text-dark" id="msgModalSubject">Subject Line Goes Here</h4>
                                </div>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>

                            <div class="modal-body p-4">

                                <div class=" d-flex justify-content-between align-items-center mb-4">
                                    <div class="d-flex align-items-center">
                                        <img src="https://ui-avatars.com/api/?name=John+Doe&background=212529&color=fff"
                                            class="rounded-circle me-3 border" width="50" height="50" id="msgModalAvatar">
                                        <div>
                                            <h6 class="fw-bold mb-0 text-dark" id="msgModalSender">John Doe</h6>
                                            <small class="text-muted" id="msgModalEmail">john.doe@example.com</small>
                                        </div>
                                    </div>
                                    <div class="text-end">
                                        <small class="text-secondary fw-bold d-block" id="msgModalDate">Jan 28, 2026</small>
                                        <small class="text-muted" id="msgModalTime">10:30 AM</small>
                                    </div>
                                </div>

                                <div class="bg-light p-4 rounded-4 border mb-3">
                                    <p class="text-dark lh-lg mb-0" id="msgModalContent" style="white-space: pre-wrap;">
                                        Hello,

                                        I am writing to inquire about the delivery status of my recent order #4458. It has been 3 days since the estimated delivery date.

                                        Could you please check this for me?

                                        Thanks,
                                        John
                                    </p>
                                </div>

                                <div>
                                    <small class="fw-bold text-secondary text-uppercase" style="font-size: 11px;">Attachments</small>
                                    <div class="d-flex gap-2 mt-2">
                                        <div class="border rounded-3 px-3 py-2 d-flex align-items-center bg-white cursor-pointer">
                                            <i class="bi bi-file-earmark-image text-danger me-2"></i>
                                            <span class="small fw-bold text-dark">screenshot_error.jpg</span>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <div class="modal-footer border-top-0 p-4 bg-light rounded-bottom-4 d-block">
                                <h6 class="fw-bold text-dark mb-2"><i class="bi bi-reply-fill me-2"></i>Quick Reply</h6>
                                <textarea class="form-control mb-3" rows="3" placeholder="Write your response here..."></textarea>
                                <div class="d-flex justify-content-between align-items-center">
                                    <button type="button" class="btn btn-outline-danger border-0 fw-bold btn-sm">
                                        <i class="bi bi-trash me-2"></i>Delete Message
                                    </button>
                                    <button type="button" class="btn btn-dark fw-bold px-4 rounded-2">
                                        Send Reply <i class="bi bi-send-fill ms-2"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/timestore/public/assets/Script/Admin/messages.js"></script>