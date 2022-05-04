import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class JavaTest { // written & compiled using JUnit 5 in Eclipse IDE

	Clerk c1 = new Clerk("Clerk 1", .1, new Haphazard());
	Clerk c2 = new Clerk("Clerk 2", .01, new Manual());
	Clerk c3 = new Clerk("Clerk 3", 0, new Electronic());
//	Clerk[] employees = {c1, c2, c3};
	
	// 19 test cases to pass
	// Assumption of output logging: system will fail to print corresponding `println` statement if test fails, opting to exit

	@Test
	void ClerkTest() {
		System.out.println("--- TESTING CLERK ---");
		assertEquals("Clerk 1", c1.getName());
		System.out.println("Clerk Name PASSED, Name out: " + c1.getName() + ", Name passed: Clerk 1");
		assertEquals(1000, c1.CheckRegister(50));
		System.out.println("Clerk Register Check PASSED, went to store when balance was 50");
		assertEquals(0, c1.CheckRegister(900));
		System.out.println("Clerk Register Check PASSED, did NOT go to store when balance was 900");
		assertEquals(0, c1.getDaysInARow());
		c1.LeaveTheStore();
		assertEquals(1, c1.getDaysInARow());
		System.out.println("Clerk Leave PASSED, Days after 1 leave: " + c1.getDaysInARow());
	}
	
	@Test
	void ItemTest() {
		System.out.println("--- TESTING ITEMS ---");
		Item insString = new InstrumentString();
		Item guitar = new Guitar();
		Item ig = ItemGenerator.generateSpecificItem(ItemType.CASSETTE);
		assertEquals("Guitar", ((InstrumentString)insString).type);
		System.out.println("Instrument String PASSED, Type set to: " + ((InstrumentString)insString).type);
		assertEquals(ItemType.INSTRUMENTSTRING, insString.getType());
		System.out.println("Type PASSED, Instrument String Type: " + insString.getType());
		assertTrue(insString.getType() != guitar.getType());
		System.out.println("Type Overriding PASSED, Instrument String v. Guitar Type: " + insString.getType() + " v. " + guitar.getType());
		assertEquals("The Mighty Pythons", ((Cassette)ig).band);
		System.out.println("Item Factory PASSED, cassette created with band: " + ((Cassette)ig).band);
		
		
	}
	
	@Test
	void TuningTest() {
		System.out.println("--- TESTING TUNING METHODS ---");
		Haphazard h = new Haphazard();
		Manual m = new Manual();
		Electronic e = new Electronic();
		Player p = new CDPlayer();
		boolean hState = h.tunePlayer(p);
		boolean mState = m.tunePlayer(p);
		boolean eState = e.tunePlayer(p);
		assertTrue(eState);
		System.out.println("3 Tuning Types PASSED, pre-state: " + p.getState());
		System.out.println("Tune Results (H, M, E): " + hState + " " + mState + " " + eState);
	}
	
	@Test
	void CustomerTest() {
		System.out.println("--- TESTING CUSTOMER ---");
		Customer c1 = new Customer(1, true);
		Customer c2 = new Customer(2, false);
		assertTrue(c1.isBuying() && !c2.isBuying());
		System.out.println("Constructor PASSED, c1 buying, c2 NOT buying");
		ItemType i = c1.getInterest();
		if (i == ItemType.BASS) {
			c1.setInterest(ItemType.FLUTE);
		} else {
			c1.setInterest(ItemType.BASS);
		}
		assertTrue(i != c1.getInterest());
		System.out.println("Interest Changing PASSED, from " + i + " -> " + c1.getInterest());
	}
	
	@Test
	void StoreTest() {
		System.out.println("--- TESTING STORE ---");
		c1.setDaysInARow(3);
		Clerk[] employees = {c1, c2, c3};
		Store testStore = new Store(employees, "test store");
		assertEquals(66, testStore.getInventory().size());
		System.out.println("Generate Starting Inventory PASSED, Items created: " + testStore.getInventory().size()+ " [expected 66]");
		Clerk[] storeEmp = testStore.getEmployees();
		assertEquals(storeEmp[0].getName(), "Clerk 1");
		System.out.println("Clerk passing PASSED, C1 name: " + storeEmp[0].getName() + " [expected `Clerk 1`]");
		
		
	}
	
	@Test
	void BundleTest() {
		System.out.println("--- TESTING STRING BUNDLE ---");
		StringBundle e = new ElectricStringBundle();
		StringBundle a = new AnalogStringBundle();
		assertTrue(e.getType() != a.getType());
		System.out.println("Decorator Class PASS, Bundle types are not the same");
	}
	
	@Test
	void GuitarKitTest() {
		System.out.println("--- TESTING GUITARKIT ---");
		Component c1 = new Component("C1", 15);
		Component c2 = new Component("C2", 0);
		Component c3 = new Component("C3", -5);
		assertTrue(c3.getName() == "C3" && c3.getPrice() == -5);
		System.out.println("Component Creation PASSED, name: " + c3.getName() + ", price: " + c3.getPrice() + " [expected `C3`, -5]");
		ArrayList<Component> comps = new ArrayList<Component>();
		comps.add(c1);
		comps.add(c2);
		comps.add(c3);
		CustomGuitarKit cgk = new CustomGuitarKit(comps);
		assertEquals(cgk.getListPrice(), 10);
		assertEquals(cgk.getName(), "Custom Guitar - C1 C2 C3 ");
		System.out.println("Custom Guitar Kit Creation PASSED, name: " + cgk.getName() + " [expected `Custom Guitar - C1 C2 C3 `]");
		System.out.println("Custom Guitar Kit Price Logic PASSED, price: " + cgk.getListPrice() + " [expected 10]");
	}
	
}
