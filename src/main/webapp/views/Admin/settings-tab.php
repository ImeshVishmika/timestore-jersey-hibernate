 <div class="tab-pane" id="settings" role="tabpanel" aria-labelledby="settings-tab" tabindex="0">
     <div class="mb-5">
         <h2 class="fw-bold text-dark display-6">Platform Settings</h2>
         <p class="text-muted small mb-0">Configure store parameters and security</p>
     </div>

     <div class="row g-4">

         <div class="col-12 col-xl-6">
             <div class="card border-0 shadow rounded-4 h-100">
                 <div class="card-header d-flex justify-content-between bg-white border-bottom-0 p-4 pb-0">
                     <div class="d-flex align-items-center">
                         <div class="bg-danger bg-opacity-10 text-danger p-2 rounded-3 me-3">
                             <i class="bi bi-truck fs-5"></i>
                         </div>
                         <h5 class="fw-bold mb-0">Delivery Configuration</h5>
                     </div>
                     <div class="text-end mt-1">
                         <button class="btn btn-link text-decoration-none small fw-bold text-danger" data-bs-toggle="modal" data-bs-target="#deliveryModal">
                             <i class="bi bi-plus-circle me-1"></i>Add New Delivery Method
                         </button>
                     </div>

                 </div>
                 <div class="card-body p-4">
                     <p class="text-muted small mb-4">Update the delivery fees calculated during customer checkout.</p>

                     <div id="deliveryDetailsTableBody"></div>

                     <!-- <div class="row g-3 align-items-end mb-3">
                                            <div class="col-8">
                                                <label class="form-label fw-bold text-secondary small">Colombo Delivery Fee</label>
                                                <div class="input-group">
                                                    <span class="input-group-text bg-light border-end-0">Rs.</span>
                                                    <input type="number" class="form-control border-start-0" value="350">
                                                </div>
                                            </div>
                                            <div class="col-4">
                                                <span class="badge bg-success bg-opacity-10 text-success w-100 py-2">Active</span>
                                            </div>
                                        </div>

                                        <div class="row g-3 align-items-end mb-4">
                                            <div class="col-8">
                                                <label class="form-label fw-bold text-secondary small">Islandwide Delivery Fee</label>
                                                <div class="input-group">
                                                    <span class="input-group-text bg-light border-end-0">Rs.</span>
                                                    <input type="number" class="form-control border-start-0" value="500">
                                                </div>
                                            </div>
                                            <div class="col-4">
                                                <span class="badge bg-success bg-opacity-10 text-success w-100 py-2">Active</span>
                                            </div>
                                        </div> -->


                 </div>
             </div>
         </div>

         <div class="col-12 col-xl-6">
             <div class="card border-0 shadow rounded-4 h-100">
                 <div class="card-header bg-white border-bottom-0 p-4 pb-0">
                     <div class="d-flex align-items-center">
                         <div class="bg-dark bg-opacity-10 text-dark p-2 rounded-3 me-3">
                             <i class="bi bi-shield-lock fs-5"></i>
                         </div>
                         <h5 class="fw-bold mb-0">Admin Security</h5>
                     </div>
                 </div>
                 <div class="card-body p-4">
                     <form>
                         <div class="mb-3">
                             <label class="form-label fw-bold text-secondary small">Current Email</label>
                             <input type="text" class="form-control bg-light" value="admin@timestore.com" readonly>
                         </div>
                         <div class="r g-2 mb-4">
                             <div class="col-12">
                                 <label class="form-label fw-bold text-secondary small">Old Password</label>
                                 <input type="password" class="form-control" placeholder="••••••••">
                             </div>
                             <div class="col-12">
                                 <label class="form-label fw-bold text-secondary small">New Password</label>
                                 <input type="password" class="form-control" placeholder="••••••••">
                             </div>
                             <div class="col-12">
                                 <label class="form-label fw-bold text-secondary small">Confirm Password</label>
                                 <input type="password" class="form-control" placeholder="••••••••">
                             </div>
                         </div>
                         <div class="text-end">
                             <button class="btn btn-outline-danger fw-bold rounded-1 px-4">Change Password</button>
                         </div>
                     </form>
                 </div>
             </div>
         </div>

         <div class="col-12">
             <div class="card border-0 shadow rounded-4">
                 <div class="card-body p-4">
                     <div class="d-flex align-items-center justify-content-between">
                         <div class="d-flex align-items-center">
                             <div class="bg-warning bg-opacity-10 text-warning p-3 rounded-3 me-3">
                                 <i class="bi bi-power fs-4"></i>
                             </div>
                             <div>
                                 <h5 class="fw-bold mb-1">Maintenance Mode</h5>
                                 <p class="text-muted small mb-0">Disable the public-facing website for updates. Admin panel remains active.</p>
                             </div>
                         </div>
                         <div class="form-check form-switch">
                             <input class="form-check-input p-2" type="checkbox" role="switch" id="maintenanceSwitch" style="width: 3.5rem; height: 2rem;">
                         </div>
                     </div>
                 </div>
             </div>
         </div>

         <div class="col-12">
             <div class="card border-0 shadow rounded-4">
                 <div class="card-header bg-white border-bottom-0 p-4 pb-0">
                     <h5 class="fw-bold">Admin Access Control</h5>
                 </div>
                 <div class="card-body p-4">
                     <div class="table-responsive">
                         <table class="table align-middle">
                             <thead class="bg-light">
                                 <tr>
                                     <th class="ps-3 text-secondary small">Admin User</th>
                                     <th class="text-secondary small">Role</th>
                                     <th class="text-secondary small">Last Active</th>
                                     <th class="text-end pe-3 text-secondary small">Action</th>
                                 </tr>
                             </thead>
                             <tbody>
                                 <tr>
                                     <td class="ps-3">
                                         <div class="d-flex align-items-center">
                                             <div class="bg-dark text-white rounded-circle d-flex align-items-center justify-content-center me-2" style="width: 32px; height: 32px;">A</div>
                                             <span class="fw-bold small">Admin User (You)</span>
                                         </div>
                                     </td>
                                     <td><span class="badge bg-dark text-white">Super Admin</span></td>
                                     <td class="small text-secondary">Just Now</td>
                                     <td class="text-end pe-3"><button class="btn btn-sm btn-light border" disabled>Edit</button></td>
                                 </tr>
                                 <tr>
                                     <td class="ps-3">
                                         <div class="d-flex align-items-center">
                                             <div class="bg-light text-secondary border rounded-circle d-flex align-items-center justify-content-center me-2" style="width: 32px; height: 32px;">S</div>
                                             <span class="fw-bold small">Support Staff</span>
                                         </div>
                                     </td>
                                     <td><span class="badge bg-light text-dark border">Editor</span></td>
                                     <td class="small text-secondary">2 hrs ago</td>
                                     <td class="text-end pe-3"><button class="btn btn-sm btn-outline-danger border-0"><i class="bi bi-trash-fill"></i></button></td>
                                 </tr>
                             </tbody>
                         </table>
                     </div>
                     <div class="mt-3">
                         <button class="btn btn-light border fw-bold text-dark" data-bs-toggle="modal" data-bs-target="#addAdminModal">
                             <i class="bi bi-plus-lg me-2"></i>Add New User
                         </button>
                     </div>
                 </div>
             </div>
         </div>

     </div>

     <div class="modal fade" id="addAdminModal" tabindex="-1" aria-hidden="true">
         <div class="modal-dialog modal-dialog-centered">
             <div class="modal-content rounded-4 border-0 shadow-lg">

                 <div class="modal-header border-bottom-0 p-4 pb-0">
                     <h5 class="modal-title fw-bold">Grant Admin Access</h5>
                     <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                 </div>

                 <div class="modal-body p-4">
                     <p class="text-muted small mb-3">Create a new account for staff members. They will be able to access this panel.</p>


                     <div class="row g-3 mb-3">
                         <div class="col-6">
                             <label class="form-label fw-bold text-secondary small">First Name</label>
                             <input type="text" class="form-control rounded-2" name="fname" placeholder="John" required>
                         </div>
                         <div class="col-6">
                             <label class="form-label fw-bold text-secondary small">Last Name</label>
                             <input type="text" class="form-control rounded-2" name="lname" placeholder="Doe" required>
                         </div>
                     </div>

                     <div class="mb-3">
                         <label class="form-label fw-bold text-secondary small">Email Address</label>
                         <input type="email" class="form-control rounded-2" name="email" placeholder="staff@timestore.com" required>
                     </div>

                     <div class="mb-3">
                         <label class="form-label fw-bold text-secondary small">Temporary Password</label>
                         <div class="input-group">
                             <span class="input-group-text bg-light border-end-0"><i class="bi bi-key"></i></span>
                             <input type="password" class="form-control border-start-0" name="password" placeholder="••••••••" required>
                         </div>
                     </div>

                     <div class="mb-4">
                         <label class="form-label fw-bold text-secondary small">Access Level</label>
                         <select class="form-select rounded-2 fw-bold text-secondary" name="role">
                             <option value="1">Super Admin (Full Access)</option>
                             <option value="2">Editor (Products & Orders)</option>
                             <option value="3">Viewer (Read Only)</option>
                         </select>
                     </div>

                     <div class="alert alert-light border d-flex align-items-center p-2 rounded-3 small" role="alert">
                         <i class="bi bi-info-circle-fill text-primary flex-shrink-0 me-2"></i>
                         <div>
                             New admins will need to verify their email upon first login.
                         </div>
                     </div>

                     <div class="d-grid mt-3">
                         <button class="btn btn-danger py-2 fw-bold rounded-2 shadow-sm">Create Account</button>
                     </div>
                 </div>

             </div>
         </div>
     </div>

     <div class="modal fade" id="deliveryModal" tabindex="-1" aria-hidden="true">
         <div class="modal-dialog modal-dialog-centered">
             <div class="modal-content rounded-4 border-0 shadow-lg">

                 <div class="modal-header border-bottom-0 p-4 pb-0">
                     <h5 class="modal-title fw-bold">Update Delivery Rates</h5>
                     <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                 </div>

                 <div class="modal-body p-4">
                     <p class="text-muted small mb-4">Select a delivery method to update its pricing and estimated delivery time.</p>

                     <form id="updateDeliveryForm" method="POST">

                         <div class="mb-3">
                             <label class="form-label fw-bold text-secondary small">Delivery Zone / Method</label>
                             <select class="form-select rounded-2" name="delivery_id" id="delivery_selector">

                             </select>
                         </div>

                         <div class="row g-3">
                             <div class="col-md-6">
                                 <label class="form-label fw-bold text-secondary small">Delivery Fee</label>
                                 <div class="input-group">
                                     <span class="input-group-text bg-light border-end-0 text-secondary">Rs.</span>
                                     <input type="number" class="form-control border-start-0 fw-bold" name="new_price" id="price_input" placeholder="0.00" required>
                                 </div>
                             </div>

                             <div class="col-md-6">
                                 <label class="form-label fw-bold text-secondary small">Est. Days</label>
                                 <div class="input-group">
                                     <input type="number" class="form-control border-end-0 fw-bold" name="new_days" id="days_input" placeholder="1-3" required>
                                     <span class="input-group-text bg-light border-start-0 text-secondary">Days</span>
                                 </div>
                             </div>
                         </div>

                         <div class="alert alert-warning d-flex align-items-center mt-4 p-2 rounded-3 small" role="alert">
                             <i class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"></i>
                             <div>
                                 Changes will affect all <strong>new</strong> orders immediately.
                             </div>
                         </div>

                         <div class="d-grid mt-3">
                             <button type="submit" class="btn btn-danger py-2 fw-bold rounded-2 shadow-sm">Save Changes</button>
                         </div>

                     </form>
                 </div>
             </div>
         </div>
     </div>
 </div>

 <script src="/timestore/public/assets/Script/Admin/settings.js"></script>