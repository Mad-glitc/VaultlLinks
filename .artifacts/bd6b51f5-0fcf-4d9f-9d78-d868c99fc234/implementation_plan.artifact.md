# Implementation Plan - Collection Deletion, Notepad Section, and Accent Color Fix

This plan covers three improvements to VaultLinks:
1. Adding a "Delete Collection" feature.
2. Adding a "Notepad" section for Links with timestamped entries.
3. Fixing the accent color selection in Settings.

## Proposed Changes

### 1. Collection Deletion
Users should be able to delete a collection from its detail screen.

#### [MODIFY] [CollectionDetailViewModel.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/presentation/screen/collections/CollectionDetailViewModel.kt)
- Add `deleteCollection()` method that calls `collectionRepository.delete()`.
- Add a state to track if deletion is successful to trigger navigation back.

#### [MODIFY] [CollectionDetailScreen.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/presentation/screen/collections/CollectionDetailScreen.kt)
- Add a `Delete` icon to the `TopAppBar` actions.
- Implement a confirmation `AlertDialog`.
- Call `onBack()` after successful deletion.

---

### 2. Notepad Section (for Links)
Enhance the existing "Personal Notes" into a "Notepad" section that saves individual notes with timestamps.

#### [NEW] [LinkNoteEntity.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/data/local/entity/LinkNoteEntity.kt)
- Fields: `id`, `linkId` (ForeignKey to `LinkEntity`), `text`, `createdAt`.

#### [NEW] [LinkNoteDao.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/data/local/dao/LinkNoteDao.kt)
- Methods to insert, delete, and observe notes for a specific link.

#### [MODIFY] [VaultDatabase.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/data/local/VaultDatabase.kt)
- Add `LinkNoteEntity` to `entities`.
- Add `linkNoteDao()` abstract function.
- Increment version to 2 and add `AutoMigration(from = 1, to = 2)`.

#### [MODIFY] [LinkDetailViewModel.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/presentation/screen/linkdetail/LinkDetailViewModel.kt)
- Inject `LinkNoteRepository` (or use DAO directly).
- Add methods to `addNote(text: String)` and `deleteNote(id: Long)`.
- Expose a flow of `LinkNote`s.

#### [MODIFY] [LinkDetailScreen.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/presentation/screen/linkdetail/LinkDetailScreen.kt)
- Replace/Extend the current "Personal Notes" section with a "Notepad" section.
- Display a list of notes with `DateUtils.relativeTime(createdAt)` or a formatted date.

---

### 3. Accent Color Fix
The accent color circles in Settings are not interactive.

#### [MODIFY] [SettingsScreen.kt](file:///C:/Users/Madhav%20Singla/Downloads/VaultLinks/VaultLinks/app/src/main/java/com/vaultlinks/app/presentation/screen/settings/SettingsScreen.kt)
- Add `.clickable { viewModel.setAccentColor(hex) }` to the accent color `Surface` elements.

## Verification Plan

### Automated Tests
- N/A (Project currently lacks test infrastructure).

### Manual Verification
1. **Accent Color**: Open Settings, tap different colors, and verify the `CheckCircle` moves and the UI accent color changes (if wired correctly to theme).
2. **Collection Delete**: Open a collection, tap delete, confirm, and verify you are navigated back and the collection is gone.
3. **Notepad**: Open a link detail, add a note, verify it appears with today's date, add another, verify the log grows.
