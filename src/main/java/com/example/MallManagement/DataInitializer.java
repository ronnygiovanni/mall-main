package com.example.MallManagement;

import com.example.MallManagement.model.*;
import com.example.MallManagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MallRepository mallRepo;
    private final FloorRepository floorRepo;
    private final ShopRepository shopRepo;
    private final CustomerRepository custRepo;
    private final PurchaseRepository purchRepo;
    private final SecurityStaffRepository secRepo;
    private final MaintenanceStaffRepository maintRepo;
    private final StaffAssignmentRepository assignRepo;
    private final MaintenanceTaskRepository taskRepo;
    private final ElectricalAssetRepository assetRepo;

    public DataInitializer(MallRepository mallRepo, FloorRepository floorRepo, ShopRepository shopRepo,
                           CustomerRepository custRepo, PurchaseRepository purchRepo, SecurityStaffRepository secRepo,
                           MaintenanceStaffRepository maintRepo, StaffAssignmentRepository assignRepo,
                           MaintenanceTaskRepository taskRepo, ElectricalAssetRepository assetRepo) {
        this.mallRepo = mallRepo;
        this.floorRepo = floorRepo;
        this.shopRepo = shopRepo;
        this.custRepo = custRepo;
        this.purchRepo = purchRepo;
        this.secRepo = secRepo;
        this.maintRepo = maintRepo;
        this.assignRepo = assignRepo;
        this.taskRepo = taskRepo;
        this.assetRepo = assetRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mallRepo.count() > 0) return; // Stop if data already exists

        System.out.println("Generating 10+ records per entity...");

        // 1. Malls
        for (int i = 1; i <= 10; i++) {
            mallRepo.save(new Mall("Mall " + i, "City " + i));
        }

        // 2. Floors (2 per mall = 20 floors)
        for (Mall mall : mallRepo.findAll()) {
            floorRepo.save(new Floor(0, mall));
            floorRepo.save(new Floor(1, mall));
        }

        // 3. Shops (1 per floor = 20 shops)
        int sCount = 1;
        for (Floor floor : floorRepo.findAll()) {
            shopRepo.save(new Shop("Shop " + sCount, "Owner " + sCount, 100.0, 5, floor));
            sCount++;
        }

        // 4. Customers
        for (int i = 1; i <= 10; i++) {
            custRepo.save(new Customer("Customer " + i, "EUR"));
        }

        // 5. Purchases
        Customer c1 = custRepo.findAll().get(0);
        Shop s1 = shopRepo.findAll().get(0);
        for (int i = 1; i <= 10; i++) {
            purchRepo.save(new Purchase(50.0 * i, c1, s1));
        }

        // 6. Security Staff
        for (int i = 1; i <= 10; i++) {
            secRepo.save(new SecurityStaff("Officer " + i, 3000, "BADGE-" + i));
        }

        // 7. Maintenance Staff
        for (int i = 1; i <= 10; i++) {
            maintRepo.save(new MaintenanceStaff("Worker " + i, 2500, MaintenanceStaff.Type.Electrical));
        }

        // 8. Assignments
        Staff staff = maintRepo.findAll().get(0);
        Floor floor = floorRepo.findAll().get(0);
        for (int i = 1; i <= 10; i++) {
            assignRepo.save(new StaffAssignment(floor, staff, StaffAssignment.Shift.Morning));
        }

        // 9. Tasks
        StaffAssignment assignment = assignRepo.findAll().get(0);
        for (int i = 1; i <= 10; i++) {
            taskRepo.save(new MaintenanceTask("Fix Thing " + i, MaintenanceTask.Status.Planned, 60, floor, assignment));
        }

        // 10. Assets
        for (int i = 1; i <= 10; i++) {
            assetRepo.save(new ElectricalAsset(ElectricalAsset.Type.Lift, ElectricalAsset.Status.Working, floor, assignment));
        }

        System.out.println("Data Generation Complete.");
    }
}