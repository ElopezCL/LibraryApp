package com.example.libraryapp.presentation.ui
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.libraryapp.R
import com.example.libraryapp.databinding.DialogAddBookBinding
import com.example.libraryapp.databinding.FragmentBookingDetailBinding
import com.example.libraryapp.presentation.viewmodel.BookDetailViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class BookingDetailFragment : Fragment(R.layout.fragment_booking_detail) {

    private var _binding: FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val updateButton = binding.updateButton
        val bookId = arguments?.getInt("bookId") ?: return

        updateButton.setOnClickListener {
            showUpdateDialog()
        }

        viewModel.loadBook(bookId)
        viewModel.book.observe(viewLifecycleOwner) { book ->
            book?.let {
                binding.titleText.text = it.title
                binding.authorText.text = it.author
                binding.yearText.text = it.year.toString()
                binding.descriptionText.text = it.description

                val isAvailable = it.isAvailable
                binding.availabilityChip.apply {
                    text = if (isAvailable) "Available" else "Checked Out"
                    chipBackgroundColor = ColorStateList.valueOf(
                        requireContext().getColor(
                            if (isAvailable) R.color.available else R.color.checked_out
                        )
                    )
                    contentDescription = getString(
                        if (isAvailable) R.string.chip_available else R.string.chip_checked_out
                    )
                }
            }
        }


        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showUpdateDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(layoutInflater)
        val currentBook = viewModel.book.value ?: return
        dialogBinding.titleInput.setText(currentBook.title)
        dialogBinding.authorInput.setText(currentBook.author)
        dialogBinding.yearInput.setText(currentBook.year.toString())
        dialogBinding.descriptionInput.setText(currentBook.description)

        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { _, _ ->
                with(dialogBinding) {
                    val title = titleInput.text.toString().trim()
                    val author = authorInput.text.toString().trim()
                    val year = yearInput.text.toString().toIntOrNull()
                    val description = descriptionInput.text.toString().trim()
                    if (title.isEmpty() || author.isEmpty() || year == null || description.isEmpty()) {
                        Snackbar.make(
                            binding.root,
                            "TODOS LOS CAMPOS SON NECESARIOS!.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        return@setPositiveButton
                    }

                    val updatedBook = currentBook.copy(
                        title = title,
                        author = author,
                        year = year,
                        description = description,
                    )
                    viewModel.updateBook(updatedBook)
                }
            }
            .show()
    }

}