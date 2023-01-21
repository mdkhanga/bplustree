# bplustree

Java Implementation of B+ tree

This is a java implementation of a B+ tree. B+ tree is a data structure that is used for most relational 
database storage especially indexes. As in a B Tree , each node in a B+ tree has multiple keys, with the difference 
that only the leaf nodes have values. Since non leaf nodes do not have values, you can pack more keys 
in each node there by reducing the height of the tree.

You can use this library to store records as what is called in databases as clustered Index.

Reference: Database Management Systems by RamaKrishna and Gehrke

## Supported features
- Define your "table" schema. Column name and type.
- Supported types are integer, variable length string, float and boolean.
- CRUD operations - Create, Update, delete, read records.
- Data is stored in a file.

## Known limitations
- Api is typeless ( Objects stuffed into non generic lists and casting). It was a conscious 
decision to not use generics. Using generics meant some code generation would be necessary.
I wanted to focus on the database part and not on nice to have stuff.

- Api is verbose. But that can be improved as we go along 

## Usage

### Define schema and create a table

```
List<Field> tableSpec = new ArrayList<>();

tableSpec.add(new Field("id", FieldType.integer));
tableSpec.add(new Field("firstname", FieldType.string, 10));
tableSpec.add(new Field("lastname", FieldType.string, 10));
tableSpec.add(new Field("salary", FieldType.integer));

		BPlusTreeImpl tree = new BPlusTreeImpl(null, "empindex100.db",
				Arrays.asList("lastname", "firstname"), tableSpec);

```
### Create or add records to the table
```
tree.insert(Arrays.asList(1, "John", "Doe", 5000));
tree.insert(Arrays.asList(2, "Joe", "Pakin", 5200));
tree.insert(Arrays.asList(3, "Anglo", "Nagap", 5400));
```
### find records
```
// returned val is a list of fields
List emps = tree.find(Arrays.asList("Pakin","Joe");
```
### delete records
```
tree.delete(Arrays.asList("Nagap","Anglo"));
```
### validate tree

```
// Check that the tree is valid
assertTrue(tree.isTreeValid());
```
### Close the tree
```
tree.close();
```

