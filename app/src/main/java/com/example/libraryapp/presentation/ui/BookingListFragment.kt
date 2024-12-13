package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.databinding.DialogAddBookBinding
import com.example.libraryapp.databinding.FragmentBookingListBinding
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.viewmodel.BookListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
private const val IdBook = "bookId"
class BookingListFragment : Fragment(R.layout.fragment_booking_list) {

    private var _binding: FragmentBookingListBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    private val viewModel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<FloatingActionButton>(R.id.addButton)
        setupRecyclerView()

        button.setOnClickListener {
            showAddBookDialog()
        }

        viewModel.books.observe(viewLifecycleOwner) { books ->
            bookAdapter.submitList(books)
            binding.emptyView.isVisible = books.isEmpty()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter { book ->
            val bundle = Bundle().apply {
                putInt(IdBook, book.id)
            }
            findNavController().navigate(
                R.id.action_bookListFragment_to_bookDetailFragment,
                bundle
            )
        }
        binding.recyclerView.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }


    private fun showAddBookDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Book")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                with(dialogBinding) {
                    val title = dialogBinding.titleInput.text.toString().trim()
                    val author = dialogBinding.authorInput.text.toString().trim()
                    val yearString = dialogBinding.yearInput.text.toString().trim()
                    val description = dialogBinding.descriptionInput.text.toString().trim()

                    if (title.isNotBlank() && author.isNotBlank() && yearString.isNotBlank() && description.isNotBlank()) {
                        val year = yearString.toIntOrNull()
                        if(year != null){
                            viewModel.addBook(title,author,year,description)
                        }else{
                            Toast.makeText(requireContext(), "Por favor ingresa un año valido", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(requireContext(), "Por favor ingresa todos los validos.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}